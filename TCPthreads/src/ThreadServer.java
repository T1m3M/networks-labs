import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ThreadServer {
	
	public static String[][] grades = {
			{"Mohamed", "10"},
			{"Sara", "15"},
			{"Ahmed", "25"},
			{"Nabil", "50"},
			{"Amira", "75"},
			{"Abdelrahman", "100"}
	};
	
	// searching for the name in grades 2d array
	// if found return the position, otherwise return -1
	public static int findName(String key) {
		
		for(int i = 0; i < grades.length; i++) {
			if( grades[i][0].equals(key) ) {
				return i;
			}
		}
		
		return -1;
	}

	public static void main(String[] args) {

		ServerSocket myServer = null;

		try {

			myServer = new ServerSocket(1234);

		} catch (IOException e) {
			System.out.println("Can not connect to the server! :(");
			System.exit(1);
		}

		while (true) {
			try {

				Socket myConnection = myServer.accept();
				System.out.println("\nA new client joined the server!");

				// create an object for the client for the thread
				ClientHandler cHandler = new ClientHandler(myConnection);
				cHandler.start();

			} catch (IOException e) {
				System.out.println("Can not accept the connection! :(");
				System.exit(1);
			}

		}

	}

}

class ClientHandler extends Thread {
	private Socket client;
	private Scanner input;
	private PrintWriter output;
	private int key;

	public ClientHandler(Socket soc) {
		client = soc; // assigns the client connection to the current connection

		try {
			// get the client's streams
			input = new Scanner(client.getInputStream());
			output = new PrintWriter(client.getOutputStream(), true);

		} catch (IOException e) {
			System.out.println("Can not assign the client's streams! :(");

		}
	}

	// the interaction between the client and the server inside the thread
	public void run() {
		String msgIn = ""; // from client

		while (!msgIn.equals("q")) {
			
			msgIn = input.nextLine(); // gets the request
			
			key = ThreadServer.findName(msgIn);
			
			// sends the response
			if (key != -1) {
				output.println("Grade = " + ThreadServer.grades[key][1]);
			} else {
				output.println("Name is not found!");
			}
		}

		try {
			client.close();
			System.out.println("\nA client signed out!");

		} catch (IOException e) {
			System.out.println("Can not close the client's connection! :(");
		}
	}
}
