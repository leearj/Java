import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.UnknownHostException;

public class EX3Client {
	private static Socket mySocket;
	private static DataInputStream in;
	private static DataOutputStream out;
	private static byte[] rawBytes; // received_raw_bytes in byte form.

	public static void main(String[] args) throws UnknownHostException, IOException {
		connect();
		receive();
		send();
		responseListener();
		disconnect();
	}

	private static void connect() throws UnknownHostException, IOException {
		// Connects with Server.
		mySocket = new Socket("18.221.102.182", 38103);
		in = new DataInputStream(mySocket.getInputStream());
		out = new DataOutputStream(mySocket.getOutputStream());
		System.out.println("Connected to the Server.");
	}

	private static void receive() throws IOException {
		int size = in.read();
		System.out.println("Reading " + size + " bytes.");
		rawBytes = new byte[size]; // Size based on the value of previous byte.

		for (int i = 0; i < rawBytes.length; i++)
			rawBytes[i] = in.readByte();

		System.out.print("Data received:\n");
		String dataReceived = "";
		for (int i = 0; i < rawBytes.length; i++)
			dataReceived += Integer.toHexString((int) rawBytes[i] & 0xFF).toUpperCase();
		for (int i = 0; i < dataReceived.length(); i++) {
			if (i != 0 && i % 20 == 0)
				System.out.println();
			System.out.print(dataReceived.charAt(i));
		}
		System.out.println();
	}

	public static short checksum(byte[] b) throws IOException {
		int shortSum = 0; // returning value.

		for (int i = 0; i < rawBytes.length - 1; i += 2) { // Jumps up by two and appropriately add together.
			byte b1 = (byte) rawBytes[i];
			byte b2 = (byte) rawBytes[i + 1];
			shortSum += ((b1 << 8 & 0xFF00) + (b2 & 0xFF));
			if ((shortSum & 0xFFFF0000) > 0) {
				shortSum &= 0xFFFF;
				shortSum++;
			}
		}
		if (b.length % 2 == 1) {
			shortSum += b[b.length - 1] << 8 & 0xFF00;
		}

		// ~ performs one's complement
		return (short) ~(shortSum & 0xFFFF);
	}

	private static void send() throws IOException {
		out.write(checksum(rawBytes) >> 8);
		out.flush();
		out.write(checksum(rawBytes));
	}

	private static void responseListener() throws IOException {
		// Listen to the response
		if (in.read() == 1)
			System.out.println("Response good.");
		else if (in.read() == 0)
			System.out.println("Response bad.");
	}

	private static void disconnect() throws IOException {
		mySocket.close();
		in.close();
		out.close();
	}
}