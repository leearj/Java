import java.net.InetAddress;
import java.net.UnknownHostException;

public class Client{
	
	Client() throws UnknownHostException{
		InetAddress ip = InetAddress.getByName("localhost");
		
	}
}

//1. send the integer of the packet size first and then the message type.
//(byte) packet[0] = MessageType.START;
//packet[1] = size of the packet
//packet[2] = 

//package type[0]
//size of the packet[1]
//index of the current message: to count number of bytes[might not needed]