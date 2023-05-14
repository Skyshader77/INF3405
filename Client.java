import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.Scanner;  // Import the Scanner class
class Client { private static Socket socket;
	public static void main(String[] args) throws Exception {
// Adresse et port du serveur 
			Scanner sc = new Scanner(System.in);  // Create a Scanner object
			System.out.println("Enter port number");
		    int portID = sc.nextInt();  // Read user input
		    if (portID<5000 || portID>5050) {
		    	throw new IllegalArgumentException("Error: Port ID must be between 5000 and 5050");
		    }
		    sc.nextLine();
		    System.out.println("Enter IP address");
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
		    
		 // Création d'une nouvelle connexion aves le serveur 
		    try {
				socket = new Socket(serverAddress, portID);
				System.out.format("Serveur lancé sur [%s:%d]", serverAddress, portID);
		// Creation d'un canal entrant pour recevoir les messages envoyés, par le serveur
			    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			 // Creation of input and output readers   
			    BufferedReader in = new BufferedReader( new InputStreamReader(socket.getInputStream()));
			    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			    String fromServer;
			    String fromUser;
			    // Defines interaction between server and client 
				while ((fromServer = in.readLine()) != null) {
				    System.out.println("Server: " + fromServer);
				    if (fromServer.equals("Bye."))
				        break;
	
				    fromUser = stdIn.readLine();
				    if (fromUser != null) {
				        System.out.println("Client: " + fromUser);
				        out.println(fromUser);
				    }
				}
		    }
		    catch (UnknownHostException e) {
		        System.err.println("Don't know about host " + serverAddress);
		        System.exit(1);
		    } catch (IOException e) {
		        System.err.println("Couldn't get I/O for the connection to " +
		        		serverAddress);
		        System.exit(1);
		    }
	// Attente de la réception d'un message envoyé par le, server sur le canal
			//String helloMessageFromServer = in.readUTF();
			//System.out.println(helloMessageFromServer); 
			
		// Attente de la réception d'un message envoyé par le, server sur le canal
			//DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());
			//System.out.println();
			//String userInput=sc.nextLine(); 
			//dataOut.writeUTF(userInput);
			//System.out.println(userInput); 
	// fermeture de La connexion avec le serveur 
			socket.close();
		
		//String serverAddress = "127.0.0.1";
		//int port = 5000;

}
}
