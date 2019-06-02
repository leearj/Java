import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
import java.io.DataInputStream;
import java.io.IOException;

public class UdpClient {
	private static Socket mySocket;
	private static DataInputStream dis;
	private static OutputStream dos;
	
	public static void main(String[] args) throws IOException {
		connect();
		ipv4();
		disconnect();
	}
	
	private static void connect() throws UnknownHostException, IOException {
		mySocket = new Socket("18.221.102.182", 38005);
		dis = new DataInputStream(mySocket.getInputStream());
		dos = mySocket.getOutputStream();
	}
	
	private static void ipv4() throws UnknownHostException, IOException {
		byte[] destAddr = mySocket.getInetAddress().getAddress();
		byte[] srcAddr = { 127, 0, 0, 1 };
		int version = 4;
		byte hlen = 5;
		int dataLength = 4;
		int defaultLength = 20;
		byte[] packet;
		int portNumber = 0;
		long msAvg = 0;

		for (int i = 0; i < 13; i++) {
			int udpLength = 8;
			int totalLength = defaultLength + dataLength;
			if (i > 0)
				totalLength += udpLength;
			packet = new byte[totalLength];
			packet[0] = (byte) ((version << 4 & 0xFF) + (hlen & 0xFF));
			packet[1] = 0;
			byte lUpper = (byte) (totalLength >>> 8);
			byte lLower = (byte) (totalLength);
			packet[2] = lUpper;
			packet[3] = lLower;
			packet[4] = 0;
			packet[5] = 0;
			packet[6] = 0x40;
			packet[7] = 0;
			packet[8] = 50;
			packet[9] = 17;
			packet[12] = srcAddr[0];
			packet[13] = srcAddr[1];
			packet[14] = srcAddr[2];
			packet[15] = srcAddr[3];
			packet[16] = destAddr[0];
			packet[17] = destAddr[1];
			packet[18] = destAddr[2];
			packet[19] = destAddr[3];
			short checkSum = checksum(packet);
			packet[10] = (byte) (checkSum >> 8 & 0xFF);	// chekcSumUpper
			packet[11] = (byte) (checkSum & 0xFF);		// checkSumLower

			if (i == 0) {
				long deadbeef = Long.decode("0xDEADBEEF");
				packet[20] = (byte) (deadbeef >> 24);
				packet[21] = (byte) (deadbeef >> 16);
				packet[22] = (byte) (deadbeef >> 8);
				packet[23] = (byte) deadbeef;
			} else {
				packet[20] = 0;
				packet[21] = 0;
				packet[22] = (byte) (portNumber >> 8 & 0xFFFF);
				packet[23] = (byte) (portNumber & 0xFF);
				packet[24] = (byte) (dataLength >> 8 & 0xFF);
				packet[25] = (byte) (dataLength & 0xFF);
				Random r = new Random();
				byte[] randBytes = new byte[dataLength];
				r.nextBytes(randBytes);
				for (int j = 28; j < packet.length; j++)
					packet[j] = randBytes[j - 28];
				byte[] pseudoHeader = new byte[20 + dataLength];
				pseudoHeader[0] = srcAddr[0];
				pseudoHeader[1] = srcAddr[1];
				pseudoHeader[2] = srcAddr[2];
				pseudoHeader[3] = srcAddr[3];
				pseudoHeader[4] = destAddr[0];
				pseudoHeader[5] = destAddr[1];
				pseudoHeader[6] = destAddr[2];
				pseudoHeader[7] = destAddr[3];
				pseudoHeader[8] = 0;
				pseudoHeader[9] = 17;
				pseudoHeader[10] = packet[24];
				pseudoHeader[11] = packet[25];
				for (int j = 12; j < pseudoHeader.length; j++)
					pseudoHeader[j] = packet[j + 8];
				short UDPCheckSum = checksum(pseudoHeader);
				packet[26] = (byte) (UDPCheckSum >> 8 & 0xFF);
				packet[27] = (byte) (UDPCheckSum & 0xFF);
			}
			
			// After the packet is filled, send it to server.
			dos.write(packet);

			// Case1: Very First HandShaking
			if (i == 0) {
				System.out.print("Handshake Response: ");
				for (int j = 0; j < 4; j++)
					System.out.print(Integer.toHexString(dis.readByte()).replaceAll("ff", "").toUpperCase());
				System.out.println();
				portNumber = ((dis.read() << 8 & 0xFFFF) + (dis.read() & 0xFF));
				System.out.println("Port number received: " + portNumber + "\n");
				dataLength = 2;
			}
			
			// Case2: 12 packets after Handshaking
			else {
				System.out.println("Sending packet with " + dataLength + " bytes of data");
				System.out.print("Response: ");
				long msStart = System.currentTimeMillis();
				for (int j = 0; j < 4; j++)
					System.out.print(Integer.toHexString(dis.readByte()).replaceAll("ff", "").toUpperCase());
				long msEnd = System.currentTimeMillis();
				System.out.print("\nRTT: " + (msEnd - msStart) + "ms\n");
				msAvg += (msEnd - msStart);
				dataLength *= 2;
			}
		}
		
		// Prints out Average RTT.
		System.out.printf("\nAverage RTT: %.2f\n", msAvg / 12.0);
		System.out.println("Disconnected from Server.");
	}

	private static void disconnect() throws IOException {
		mySocket.close();
		dis.close();
		dos.close();
		System.out.println("disconnected from server.");
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