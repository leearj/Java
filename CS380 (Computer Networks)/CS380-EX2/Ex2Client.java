import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.zip.CRC32;

import javax.xml.bind.DatatypeConverter;

/** TO-DO List **/
/* #0. Connect to the server.
 * #1. Receive (hex)Bytes.
 * #2. hex-to-decimal calculation; and store it to byte array, then print(in Hex)
 * #3. Get CRC32-value of the byte-array.
 * #4. Write to the Server.
 * #5. Listen to the server response.  
 * */

public class Ex2Client {
	private static Socket mySocket;
	private static DataInputStream in ;
	private static DataOutputStream out;
	
	public static void main(String[] args) throws IOException {
		connect();
		task();
		responseListener();
	}


	public static byte[] toByteArray(String s) {
		 return DatatypeConverter.parseHexBinary(s);
	}
	
	private static void connect() throws UnknownHostException, IOException {
		// Connects with Server.
				mySocket = new Socket("localhost", 38102);
				in = new DataInputStream(mySocket.getInputStream());
				out = new DataOutputStream(mySocket.getOutputStream());
				System.out.println("Connected to the Server.");
	}
	
	
	private static void responseListener() throws IOException {
		// Listen to the response
				if (in.readByte() == 1)
					System.out.println("Response good.");
				else if(in.readByte() == 0)
					System.out.println("Response bad.");
	}

	private static void task() throws IOException {
		// Set-up for received Bytes(100bytes).
				byte[] receivedBytes = new byte[100];
				System.out.println("Received Bytes:");
				
				// Print out Received Bytes.
				for(int i=0;i<100;i++){
					if(i%10 == 0)	// 10 values per line.
						System.out.println();
					
					// Conversion
					// Example: first-in.read():x05, second-in.read(): x0A => x05 * 16 + x0A = 80+10 = 90 
					receivedBytes[i] = (byte)((in.read() * 16 + in.read()));
					System.out.printf("%02X", receivedBytes[i]);
				}
				
				// CRC32
				CRC32 crc = new CRC32();
				byte[] crc32 = new byte[4];	// 4bytes of CRC to send later.
				crc.update(receivedBytes);
				System.out.format("\n\nGenerated CRC32: %08X.\n", crc.getValue());	
				crc32 = toByteArray(Long.toHexString(crc.getValue()));
				
				// 4. Write to the server.
				for(int i=0;i< crc32.length;i++) {
				out.write(crc32[i]);
				out.flush();
				}
	}
}