import java.io.DataOutputStream;
import java.io.IOException;
import java.io.DataInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Ipv6Client {
	private static Socket mySocket;
	private static DataOutputStream dos; // To write data(byte_array)
	private static DataInputStream dis;	// To read hexadecimal_response
	
	public static void main(String[] args) throws Exception {
		connect();
		ipv6();
		disconnect();
	}

	private static void ipv6() throws IOException {
		/*
		 * For addresses in ipv6, add prefix: add 12 more bytes to the front: [Ten: 0],
		 * and [Two: (byte)255]
		 */

		// For SourceAddr, and DestinationAddr.
		 InetAddress src = InetAddress.getByName("127.0.0.1");
		 byte[] ipv4Src = src.getAddress();
		 InetAddress dest = InetAddress.getByName("18.221.102.182");
		 byte[] ipv4Dest = dest.getAddress();
		 
		byte[] srcAddr = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, (byte)255, (byte)255, ipv4Src[0], ipv4Src[1], ipv4Src[2], ipv4Src[3]};
		byte[] destAddr = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, (byte)255, (byte)255,ipv4Dest[0],ipv4Dest[1],ipv4Dest[2],ipv4Dest[3]};

		byte[] packet;
		int payload = 2;
		int version = 6;
		
		// Send packet 12 times (12 packets)
		for (int i = 0; i < 12; i++) {
			int tLen = (40+payload);			// Total_Length's default is 40; will increase each time.
			packet = new byte[tLen];
			
			packet[0] = (byte) (version << 4 & 0xFF);
			packet[1] = 0; // Traffic_Class
			packet[2] = 0; // Flow_Label;20bits
			packet[3] = 0; // Flow_Label
			packet[4] = (byte) (payload >>> 8); // Payload_Length;16bits
			packet[5] = (byte)payload;
			packet[6] = (byte)17; // Next_Header
			packet[7] = (byte)20; // Hopt_Limit

			// Source_Address;128bits=16bytes
			for (int s = 0; s < srcAddr.length; s++) 
				packet[s+8] = srcAddr[s];	// Starts from packet[8]
			
			// Destination_Address;128bits=16bytes
			for (int d = 0; d < destAddr.length; d++) 
				packet[d+24] = destAddr[d];	// Starts from packet[24]
			
			for(int k = 40;k<packet.length;k++)
				packet[k] = 0;
			
			// Write to server, listen the 4byte response
			System.out.println("data length: "+ payload);
			dos.write(packet);
			dos.flush();
			
			System.out.print("Response: ");
			for(int r=0;r<4;r++)
				System.out.print(Integer.toHexString(dis.readByte()).replace("ff", "").toUpperCase());
			
			System.out.println("\n");
			payload *=2;
		}
	}

	private static void connect() throws UnknownHostException, IOException {
		// Connects with Server.
		mySocket = new Socket("18.221.102.182", 38004);
		dis = new DataInputStream(mySocket.getInputStream());
		dos = new DataOutputStream(mySocket.getOutputStream());

		System.out.println("Connected to the Server.\n");
	}

	private static void disconnect() throws IOException {
		mySocket.close();
		dos.close();
		System.out.println("disconnected from server.");
	}

}
