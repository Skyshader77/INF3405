import java.io.DataInputStream; 
import java.io.DataOutputStream; 
import java.net.Socket; // Application client public
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
		    System.out.println("Enter username");
		    String userID = sc.nextLine(); 
		    System.out.println("Enter password");
		    String userPassword = sc.nextLine(); 
		    
		 // Création d'une nouvelle connexion aves le serveur 
			socket = new Socket(serverAddress, portID);
			System.out.format("Serveur lancé sur [%s:%d]", serverAddress, portID);
	// Céatien d'un canal entrant pour recevoir les messages envoyés, par le serveur
			DataInputStream in = new DataInputStream(socket.getInputStream());
			
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
