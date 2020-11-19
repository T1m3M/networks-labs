import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ThreadClient {

	public static void main(String[] args) {

		InetAddress host = null;

		try {

			host = InetAddress.getLocalHost();

		} catch (UnknownHostException e) {
			System.out.println("Can not connect to the host! :(");
			System.exit(1);
		}

		Socket myConnection = null;
		String request = "", response = "";

		try {
			myConnection = new Socket(host, 1234);

			Scanner fromServer = new Scanner(myConnection.getInputStream()); // gets the response
			PrintWriter toServer = new PrintWriter(myConnection.getOutputStream(), true); // sends the request

			Scanner input = new Scanner(System.in);

			System.out.println("Connected to the server! Enter \"q\" to exit\n");
			while (!request.equals("q")) {
				
				System.out.print("Name: ");
				request = input.nextLine();

				toServer.println(request); //

				response = fromServer.nextLine();
				System.out.println(response + '\n');

			}

			fromServer.close(); // closing the streams
			input.close();

		} catch (IOException e) {
			System.out.println("Error! Connection problem! :(");
			System.exit(1);

		}

		try {
			
			System.out.println("Closing the connection...");
			myConnection.close();
			System.out.println("The connection is closed!");
			
		} catch (IOException e) {
			System.out.println("Error! Can not close the connection :(");
			System.exit(1);
		}

	}

}
