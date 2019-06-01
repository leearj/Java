import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {
	private static Socket mySocket; 	// Store my socket info.
	private static BufferedReader in; 	// To read from server.
	private static PrintWriter out;		// To write to the server.
	private static BufferedReader kb;	// To read the user's keyboard.

	public static void main(String[] args) throws Exception {
		class DataReader implements Runnable {

			@Override
			public void run() {
				while (true) {
					try {
						in = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));

						String rcvMsg = null;
						rcvMsg = in.readLine();
						if (rcvMsg != null)
							System.out.println(rcvMsg);

						if (rcvMsg.equals("Server> Name in use."))
							System.exit(0);

					} catch (IOException ie) {
						ie.printStackTrace();

					} catch (NullPointerException ne) {
						System.out.println("Securely exiting the server due to timeout.");
						break;
					}
				}
			}
		}

		connect(); // Static method1
		streamSetting(); // Static method2

		DataReader dr = new DataReader();
		Thread t = new Thread(dr);
		t.start();

		while (true) {
			String youTyped = kb.readLine();

			// Exit Sequence by keyboard.
			if (youTyped.equals("/exit"))
				System.exit(0);

			// Send it to the server.
			out.println(youTyped);
			out.flush();
		}
	}

	// Static method1: handles connecting to the server.
	public static void connect() {
		System.out.println("Attempting to connect...");
		try {
			mySocket = new Socket("18.221.102.182", 38001);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Successfully connected.");
	}

	// Static method2: handles setting up the streaming.
	public static void streamSetting() throws IOException {
		out = new PrintWriter(mySocket.getOutputStream(), true);
		kb = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Stream Setting Completed.\n");
	}
}