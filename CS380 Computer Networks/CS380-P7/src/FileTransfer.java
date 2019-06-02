import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Random;
import java.util.Scanner;
import java.util.zip.CRC32;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class FileTransfer {
	static Socket socket;
	static String fileName;
	static String hostToConnect;
	static int portNumber;
	static int fileSize = 0;

	public static void main(String[] args) throws Exception {
		if (args[0].equals("makekeys"))
			keyGen();
		else if (args[0].equals("server"))
			serverMode();
		else if (args[0].equals("client")) {
			clientMode(); // Implement
			fileName = args[1]; // fileName that contains public key
			hostToConnect = args[2]; // host to connect to(where the server is running)
			portNumber = Integer.parseInt(args[3]); // port #
		}
	}

	public static void keyGen() {
		try {
			KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
			gen.initialize(4096); // you can use 2048 for faster key generation
			KeyPair keyPair = gen.genKeyPair();
			PrivateKey privateKey = keyPair.getPrivate();
			PublicKey publicKey = keyPair.getPublic();
			try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("public.bin")))) {
				oos.writeObject(publicKey);
			}
			try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("private.bin")))) {
				oos.writeObject(privateKey);
			}
		} catch (NoSuchAlgorithmException | IOException e) {
			e.printStackTrace(System.err);
		}

	}

	private static void clientMode() throws Exception {
		// #1. Generate AES_Session_Key
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "SunJSSE");
		generator.initialize(2048);
		KeyPair keyPair = generator.generateKeyPair();
		SecretKey secretKey = new SecretKeySpec(new byte[16], "AES");
		Cipher c = Cipher.getInstance("RSA", "SunJCE");

		// #2. Encrypt the session key using the server's public key(Cipher.WRAP MODE)
		c.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
		byte[] result1 = c.doFinal(secretKey.getEncoded());

		// #3. Prompt the user to enter the path for a file to transfer.
		Scanner kb = new Scanner(System.in);
		System.out.println("Please enter the path for a file to transfer");
		String path = kb.nextLine();

		// #4. If the path is valid, ask the user to enter the desired chunk size in
		// bytes (default of 1024 bytes).
		int chunkSize = 0;
		if (isValidPath(path)) {
			System.out.println("Please type your desired chunk size in bytes: ");
			chunkSize = Integer.parseInt(kb.nextLine());
		} else {
			chunkSize = 1024;
		}

		// Converting length of file into byte array.
		fileSize = (int) new File(fileName).length();
		byte[] fsize = ByteBuffer.allocate(4).putInt(fileSize).array();

		/*
		 * #5. Send the server a StartMessage that contains the file name, length of the
		 * file in bytes, chunk size, and encrypted session key. Server should response
		 * with AckMessage with seq_number:0, otherwise seq_num:-1
		 */

		StartMessage startMsg = new StartMessage(path, fsize, chunkSize);
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
		oos.writeObject(startMsg);

		/*
		 * #6_1). send each chunk of the file in order. After each chunk, wait for the
		 * server to respond with AckMessage. The sequence number in the ACK should be
		 * the number for the next expected chunk.
		 */
		Chunk chunk = new Chunk(0, result1, checksum(result1));
		/* dos.write((byte)chunk); COMMENTED FOR NOW */

		/*
		 * #6_2). the client must first read the data from the file and store in an
		 * array based on the chunk size. It should then calculate the CRC32 value for
		 * the chunk. Finally, encrypt the chunk data using the session key. Note that
		 * the CRC32 value is for the plaintext of the chunk, not the ciphertext
		 */

		// #7. After receiving the final ACK, client can either begin a new file
		// transfer or disconnect.

	}

	public static void serverMode() {
		class SocketThreads implements Runnable {
			Socket s;

			public SocketThreads(Socket s) {
				this.s = s;
			}

			public void run() {
				try {
					DataOutputStream out = new DataOutputStream(s.getOutputStream());
					DataInputStream in = new DataInputStream(s.getInputStream());
					System.out.printf("Client connected: %s%n", s.getInetAddress().getHostAddress());
					System.out.printf("Client disconnected: %s%n", socket.getInetAddress().getHostAddress());
				} catch (SocketException e) {
					System.out.printf("Client disconnected: %s%n", socket.getInetAddress().getHostAddress());
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
		}

		try (ServerSocket serverSocket = new ServerSocket(13337)) {
			while (true) {
				socket = serverSocket.accept();
				Thread t = new Thread(new SocketThreads(socket));
				t.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (Exception e) {
			}
		}
	}

	static boolean isValidPath(String path) {
		try {
			Paths.get(path);
		} catch (InvalidPathException | NullPointerException ex) {
			return false;
		}
		return true;
	}

	public static short checksum(byte[] b) throws IOException {
		int shortSum = 0; // returning value.

		for (int i = 0; i < b.length - 1; i += 2) { // Jumps up by two and appropriately add together.
			byte up = (byte) b[i];
			byte low = (byte) b[i + 1];
			int temp = ((up << 8 & 0xFF00) + (low & 0x00FF));
			shortSum += temp;

			if ((shortSum & 0xFFFF0000) > 0) {
				shortSum &= 0xFFFF;
				shortSum++;
			}
		}

		System.out.println();
		if (b.length % 2 == 1) {
			shortSum = shortSum + (b[b.length - 1] << 8) & 0xFF00;
		}
		if ((shortSum & 0xFFFF0000) > 0) {
			shortSum &= 0xFFFF;
			shortSum++;
		}

		// ~ performs one's complement
		short result = (short) ~(shortSum & 0xFFFF);
		return result;
	}
}
