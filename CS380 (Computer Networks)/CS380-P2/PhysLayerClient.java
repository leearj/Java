import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.UnknownHostException;

public class PhysLayerClient {
	private static Socket mySocket;
	private static DataInputStream in;
	private static DataOutputStream out;
	private static byte[] rcvBytes;
	private static int[] decodedBytes; // This will be finally sent.
	private static double baseline;		// This will be average of preamble.

	public static void main(String[] args) throws Exception, IOException {
		connect();
		receive();
		decode();
		printDecoded();
		send();
		responseListener();
	}

	private static void connect() throws UnknownHostException, IOException {
		// Connects with Server.
		mySocket = new Socket("18.221.102.182", 38002);
		in = new DataInputStream(mySocket.getInputStream());
		out = new DataOutputStream(mySocket.getOutputStream());
		System.out.println("Connected to the Server.");
	}

	private static void receive() throws IOException {

		// Preamble; find average
		int[] preamble = new int[64];
		baseline = 0.0;
		for (int i = 0; i < 64; i++) {
			preamble[i] = in.read();
			baseline += preamble[i];
		}
		baseline /= preamble.length;
		System.out.printf("Baseline established from preamble(double): %.2f\n", baseline);
		
		// For next 320 bytes, compare with baseline, store 0 or 1.
		rcvBytes = new byte[320];
		for (int i = 0; i < rcvBytes.length; i++) {
			if (in.read() > baseline) 
				rcvBytes[i] = 1;
			else
				rcvBytes[i] = 0;
		}
	}

	private static void decode() {
		byte[] nrzi = rcvBytes;		//rcvBytes look like: {1,0,0,1,1,0,1,1...(320 of them)}
		int[] b4 = new int[32];
		String b5 = "" + rcvBytes[0];	
		
		// NRZI Decoding
		for (int i = 1; i < rcvBytes.length; i++) {
			if (nrzi[i] == nrzi[i - 1])
				b5 += "0";
			else
				b5 += "1";
		}
		
		// 4B5B Decoding: Hardcoding the 4B5B table. 
		for (int i = 0; i < nrzi.length / 5; i++) {
			String binary = b5.substring(5 * i, 5 * i + 5);
			switch (binary) {
			case "11110":
				nrzi[4 * i] = 0;
				nrzi[4 * i + 1] = 0;
				nrzi[4 * i + 2] = 0;
				nrzi[4 * i + 3] = 0;
				break;
			case "01001":
				nrzi[4 * i] = 0;
				nrzi[4 * i + 1] = 0;
				nrzi[4 * i + 2] = 0;
				nrzi[4 * i + 3] = 1;
				break;
			case "10100":
				nrzi[4 * i] = 0;
				nrzi[4 * i + 1] = 0;
				nrzi[4 * i + 2] = 1;
				nrzi[4 * i + 3] = 0;
				break;
			case "10101":
				nrzi[4 * i] = 0;
				nrzi[4 * i + 1] = 0;
				nrzi[4 * i + 2] = 1;
				nrzi[4 * i + 3] = 1;
				break;
			case "01010":
				nrzi[4 * i] = 0;
				nrzi[4 * i + 1] = 1;
				nrzi[4 * i + 2] = 0;
				nrzi[4 * i + 3] = 0;
				break;
			case "01011":
				nrzi[4 * i] = 0;
				nrzi[4 * i + 1] = 1;
				nrzi[4 * i + 2] = 0;
				nrzi[4 * i + 3] = 1;
				break;
			case "01110":
				nrzi[4 * i] = 0;
				nrzi[4 * i + 1] = 1;
				nrzi[4 * i + 2] = 1;
				nrzi[4 * i + 3] = 0;
				break;
			case "01111":
				nrzi[4 * i] = 0;
				nrzi[4 * i + 1] = 1;
				nrzi[4 * i + 2] = 1;
				nrzi[4 * i + 3] = 1;
				break;
			case "10010":
				nrzi[4 * i] = 1;
				nrzi[4 * i + 1] = 0;
				nrzi[4 * i + 2] = 0;
				nrzi[4 * i + 3] = 0;
				break;
			case "10011":
				nrzi[4 * i] = 1;
				nrzi[4 * i + 1] = 0;
				nrzi[4 * i + 2] = 0;
				nrzi[4 * i + 3] = 1;
				break;
			case "10110":
				nrzi[4 * i] = 1;
				nrzi[4 * i + 1] = 0;
				nrzi[4 * i + 2] = 1;
				nrzi[4 * i + 3] = 0;
				break;
			case "10111":
				nrzi[4 * i] = 1;
				nrzi[4 * i + 1] = 0;
				nrzi[4 * i + 2] = 1;
				nrzi[4 * i + 3] = 1;
				break;
			case "11010":
				nrzi[4 * i] = 1;
				nrzi[4 * i + 1] = 1;
				nrzi[4 * i + 2] = 0;
				nrzi[4 * i + 3] = 0;
				break;
			case "11011":
				nrzi[4 * i] = 1;
				nrzi[4 * i + 1] = 1;
				nrzi[4 * i + 2] = 0;
				nrzi[4 * i + 3] = 1;
				break;
			case "11100":
				nrzi[4 * i] = 1;
				nrzi[4 * i + 1] = 1;
				nrzi[4 * i + 2] = 1;
				nrzi[4 * i + 3] = 0;
				break;
			case "11101":
				nrzi[4 * i] = 1;
				nrzi[4 * i + 1] = 1;
				nrzi[4 * i + 2] = 1;
				nrzi[4 * i + 3] = 1;
				break;
			}
		}
		
		//256 bits back to 32 bytes
		for (int i = 0; i < b4.length; i++) {
			b4[i] = nrzi[8 * i] * 128 + nrzi[8 * i + 1] * 64 + nrzi[8 * i + 2] * 32 + nrzi[8 * i + 3] * 16
					+ nrzi[8 * i + 4] * 8 + nrzi[8 * i + 5] * 4 + nrzi[8 * i + 6] * 2 + nrzi[8 * i + 7] * 1;
		}
		decodedBytes = b4;
	}
	
	private static void printDecoded() {
		System.out.print("Received 32 bytes: ");
		for (int i = 0; i < decodedBytes.length; i++) 
			System.out.print(Integer.toHexString(decodedBytes[i]).toUpperCase());
	}
	
	private static void responseListener() throws IOException {
		// Listen to the response
		if (in.readByte() == 1)
			System.out.println("\nResponse good.");
		else if (in.readByte() == 0)
			System.out.println("\nResponse bad.");
		else
			System.out.println("ERROR: Input is neither 1 nor 0");
	}

	private static void send() throws IOException {
		for(int i=0;i<decodedBytes.length;i++) {
		out.write(decodedBytes[i]);
		out.flush();
		}
	}
}