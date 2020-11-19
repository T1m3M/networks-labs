import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TCPclient {

	public static void main(String[] args) {
		
		InetAddress host = null;

		try {
			
			host = InetAddress.getLocalHost();
			
		} catch (UnknownHostException e) {
		
			System.out.println("Host not found! :(");
			System.exit(1);
			
		}
		
		try {
			Socket myConnection = new Socket(host, 1234);
			
			// from the server
			Scanner input = new Scanner(myConnection.getInputStream());
			// to the server
			PrintWriter output = new PrintWriter(myConnection.getOutputStream(), true);
			
			int num;
			
			System.out.print("Enter a numer: ");
			Scanner n = new Scanner(System.in);
			num = n.nextInt();

			output.println(num);
			
			String response;
			response = input.nextLine();
			
			System.out.println(response);
			
			input.close();
			n.close();
			myConnection.close();
			
		} catch (IOException e) {
			
			System.out.println("Can not connect! :(");
		}

	}
	
}
