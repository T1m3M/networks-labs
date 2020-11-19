import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPserver {

	public static void main(String[] args) {
		
		DatagramSocket mySocket = null;

		try {
			System.out.println("Connecting...");
			mySocket = new DatagramSocket(1234);
			System.out.println("Connected! .. type \"q\" to exit the server");
			
		} catch (SocketException e) {
			System.out.println("Error! Can not connect to the server! :(");
			System.exit(1);
		}
		
		String PCname = "", response = "";
		byte[] buffer;
		DatagramPacket input, output;
		
		while(true) {
			buffer = new byte[256];
			
			input = new DatagramPacket(buffer, buffer.length);
			
			try {
				
				mySocket.receive(input);
				
				// gets the data with 0 offset with its full length
				PCname = new String(input.getData(), 0, input.getLength());
				
				if(PCname.equals("q")) break; // to break the loop and before the address crash
				
				InetAddress address = null;
				
				try {
					// if successful
					address = InetAddress.getByName(PCname);
					response = address.getHostAddress();
					System.out.println("Sending the IP address...\n");
					
				} catch (IOException e){
					System.out.println("Can not find the PC");
					response = "Sorry, Can not identify the PC name.";
				}
				
				// send the data to the client
				output = new DatagramPacket(response.getBytes(), response.length(), input.getAddress(), input.getPort());
				mySocket.send(output);
				
			} catch (IOException e) {
				System.out.println("Error! Connection problem! :(");
				System.exit(1);
			}
			
		}
		
		response = "\nThe server is closed!";
		
		output = new DatagramPacket(response.getBytes(), response.length(), input.getAddress(), input.getPort());
		try {
			mySocket.send(output);
		} catch (IOException e) {
			System.out.println("Error! Can not send the data :(");
			System.exit(1);
		}
		
		mySocket.close();

	}

}
