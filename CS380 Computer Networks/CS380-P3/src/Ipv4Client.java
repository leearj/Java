
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


/*
 * Send IPv4 Packet to Server. It will check, 
 * 	1) Checksum value. 
 * 	2) Size of Data. 
 * If good, the server will respond, "Good".
 * 
 */

public class Ipv4Client {
	private static Socket mySocket;
	private static DataOutputStream dos;		// To write data(byte_array)
	private static InputStreamReader isr;	// To read text(String)_1
	private static BufferedReader br;		// To read text(String)_2

	public static void main(String[] args) throws Exception {
		connect();
		ipv4();
		disconnect();
	}

	private static void ipv4() throws IOException {

		// For SourceAddr, and DestinationAddr.
		InetAddress ip = InetAddress.getByName("localhost");
		byte[] srcAddr = ip.getAddress();
		System.out.println();
		ip = InetAddress.getByName("18.221.102.182");
		byte[] destAddr = ip.getAddress();


		byte[] packet;
		
		// Version; default: 4
		int version = 4;

		// HLen(Internet Header Length);
		// The min. value is 5.
		byte hlen = 5;

		// Data_Length increments:2,4,8,16.., given by instruction sheet.
		int dlen =2; // Will complete implementation in for-loop

		// TOS; 8bits. DNI(Do Not Implement): 0
		/* (Assign 0 in for-loop) */

		// defaul_total-length is 20. but we will increment by 2 every time.
		int tlen_default = 20;

		// Total Length; size of array.
		// Note:Total_Length = Header_Length + Data_Length(2^x)
		// So, tlen = tlen_default + dlen(2^x)
		/* int tlen; */

		// Indent; 16bytes. DNI(Do Not Implement); 0
		/* (Assign 0 in for-loop) */

		// Flags; Assuming no frag; 3bit. 010 => 0100 0000 => 0x40
		/* byte flags; */

		// Offset; 13bits. DNI(Do Not Implement); 0
		/* (Assign 0 in for-loop) */

		// TTL; 50, according to instruction.

		// Protocol; assume TCP; so TCP=6
		

		// Checksum; 16bits
		// Will assign value in for-loop, using checkSum()
		// from previous project.
		/* Implement in for-loop */

		// SourceAddr
		/* Implemented at the beginning */

		// DestinationAddr; 32bits
		/* Implemented at the beginning */

		// Send packet 12 times (12 packets)
		for (int i = 0; i < 12; i++) {

			// Firstly, implement data Length, depends on the current # of packet
			dlen = (int) Math.pow(2, (i + 1));
			int tlen = tlen_default + dlen;
			packet = new byte[tlen];

			packet[0] = (byte) ((version << 4 & 0xFF) + (hlen & 0xFF)); // 4bit(ver)+4bit(hlen) to make 1byte, using OR.
			packet[1] = 0; // TOS; DNI

			// Splitting total length to 2 bytes.
			byte upper = (byte) (tlen >>> 8);
			byte lower = (byte) (tlen);
			packet[2] = upper;
			packet[3] = lower;
			packet[4] = 0; // Indent; 16bits; DNI
			packet[5] = 0;
			packet[6] = 0x40; // Flags; 3bit
			packet[7] = 0; // Offset; DNI
			packet[8] = 50;
			packet[9] = 6; // Protocol


			packet[12] = srcAddr[0];
			packet[13] = srcAddr[1];
			packet[14] = srcAddr[2];
			packet[15] = srcAddr[3];

			packet[16] = destAddr[0];
			packet[17] = destAddr[1];
			packet[18] = destAddr[2];
			packet[19] = destAddr[3];
			
			// Checksum; NOTE: IF YOU PUT THIS BEFORE packet[12]~packet[19],
			// IF WON'T WORK. TOOK ME FOREVER.
			short chksum = checksum(packet);
			packet[10] = (byte) (chksum >> 8 & 0xFF); // Upper
			packet[11] = (byte) (chksum & 0xFF); // Upper
			
			for(int j=20;j<packet.length;j++) //Data with 0 filled.
				packet[j] = 0;
			
			dos.write(packet);
			System.out.println("data length: "+ dlen);
			System.out.println(br.readLine()+"\n");
			
		}

	}

	private static void connect() throws UnknownHostException, IOException {
		// Connects with Server.
		mySocket = new Socket("18.221.102.182", 38003);
		br = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
		dos = new DataOutputStream(mySocket.getOutputStream());
		
		System.out.println("Connected to the Server.");
	}

	private static void disconnect() throws IOException {
		mySocket.close();
		br.close();
		dos.close();
		System.out.println("disconnected from server.");

	}

	public static short checksum(byte[] b) throws IOException {
		int shortSum = 0; // returning value.
		
		for (int i = 0; i < b.length - 1; i += 2) { // Jumps up by two and appropriately add together.
			byte up = (byte)b[i];
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
			shortSum = shortSum+ (b[b.length - 1] << 8) & 0xFF00;
		}
		if((shortSum & 0xFFFF0000) > 0) {
			shortSum &= 0xFFFF;
			shortSum++;
		}

		// ~ performs one's complement
		short result = (short) ~(shortSum & 0xFFFF);
		return result;
	}
}
