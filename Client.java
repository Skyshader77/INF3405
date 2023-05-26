import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.Scanner;  // Import the Scanner class
/**
 * @summary The purpose of this class is to define an User class
 * @author Alexandre Nguyen & Louis-Antoine Martel-Marquis
 * @version 4 Last modified on 25/05/2023
 */ 
public class Client { private static Socket socket;
	//ATTRIBUTES
	private String serverAddress;
	private int portID;
	

	public Client(String serverAddress, int portID) {
        this.serverAddress = serverAddress;
        this.portID = portID;
    }
	
	//Methods
	public void execute() {
		 try {
				socket = new Socket(serverAddress, portID);
				System.out.format("Connexion launched to server [%s:%d]\n", serverAddress, portID);
				new ReadThread(socket, this).start();
	            new WriteThread(socket, this).start();
		    }
		    catch (UnknownHostException e) {
		        System.err.println("Don't know about host " + serverAddress);
		        System.exit(1);
		    } catch (IOException e) {
		        System.err.println("Couldn't get I/O for the connection to " +
		        		serverAddress);
		        System.exit(1);
		    }
	}
	public static void main(String[] args) throws Exception {

			Scanner sc = new Scanner(System.in);  // Create a Scanner object

		    System.out.println("Please enter desired IP server address in this format (ex: 127.0.0.1)");
			
			String serverAddress = sc.nextLine();  
			
			// Uses an Arrays method to see if the IP address is valid
			boolean ipValid = Arrays
					  .stream(serverAddress.split("."))
					  .mapToInt(Integer::parseInt)
					  .filter(x -> x >= 0 && x <= 255) // remove invalid numbers
					  .toArray().length == 4; // if the resulting length is 4, the ip is valid
		   
			if (ipValid=false) {
		    	throw new IllegalArgumentException("Error:  Invalid IP address");
		    }
		   
			//sc.nextLine();
			
			System.out.println("Enter port number");
		   
			int portID = sc.nextInt();  // Read user input
		   
			if (portID<5000 || portID>5050) {
		    	throw new IllegalArgumentException("Error: Port ID must be between 5000 and 5050");
		    }
		    
		    Client client = new Client(serverAddress, portID);
	        client.execute();

	}
}
	
