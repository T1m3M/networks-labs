import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class UDPclient {

	public static void main(String[] args) {
		
		InetAddress host = null;

		try {
			
			host = InetAddress.getLocalHost();
			
		} catch (UnknownHostException e) {
			
			System.out.println("Can't connect to the localhost!");
			System.exit(1);
			
		}
		
		try {
			DatagramSocket mySocket = new DatagramSocket();
			
			System.out.print("PC name: ");
			
			Scanner userInput = new Scanner(System.in);
			String PCname = userInput.nextLine();
			
			// Sends the PC name to the server to process
			DatagramPacket output = new DatagramPacket(PCname.getBytes(), PCname.length(), host, 1234);
			try {
				
				mySocket.send(output); // sending to the server..
				byte[] buffer = new byte[256];
				
				DatagramPacket input = new DatagramPacket(buffer, buffer.length);
				mySocket.receive(input); // getting from the server..
				
				String response = new String(input.getData(), 0, input.getLength());
				
				System.out.println(response);
				
			} catch (IOException e) {
				System.out.println("Can not send to the server!");
				System.exit(1);
			}
			
			System.out.println("\nClient is closing...");
			
			userInput.close();
			mySocket.close();
			
		} catch (SocketException e) {
			System.out.println("Connection problem!");
			System.exit(1);
		}

	}

}
