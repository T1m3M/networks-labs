import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TCPserver {
	
	public static boolean isOdd(int n) {	
		if(n % 2 == 0)
			return false;
	
		return true;
	}
	
	public static boolean isPrime(int n) {	
		
		for(int i = 2; i <= n/2; ++i) {
			
			if (n % i == 0)
				return false;
			
		}
		return true;
	
	}

	public static void main(String[] args) {

		System.out.println("Connecting...\n");

		try (ServerSocket myServer = new ServerSocket(1234)) {
			System.out.println("Connected!");
			System.out.println("Enter -1 to exit the server.");
			
			Socket myConnection = null;
			int num = 0;
			String result = "";
			
			while (num != -1) {
				myConnection = myServer.accept();
				
				// from client
				Scanner input = new Scanner(myConnection.getInputStream());
				// to the client
				PrintWriter output = new PrintWriter(myConnection.getOutputStream(), true);
				
				num = input.nextInt();
				
				if (num != -1) {
					// processing the number
					
					if (isOdd(num))
						result += num + " is odd ";
					else
						result += num + " is even ";
					
					if (isPrime(num))
						result += "and prime.";
					else
						result += "and not prime.";
					
					output.println(result);
					
					result = ""; // reset
				
				}
				else {
					output.println("Exiting the server...\n");
					myConnection.close();
				}
				
				input.close();
			}
			

		} catch (IOException e) {
			System.out.println("Error! Unable to conenct to the server!");
			System.exit(1);
		}

	}

}
