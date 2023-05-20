/**
 * @summary The purpose of this class is to define a Server class
 * @author Alexandre Nguyen & Louis-Phlippe
 * @version 2.0 Last modified on 18/05/2023
 */ 

import java.net.InetAddress; 
import java.net.InetSocketAddress; 
import java.net.ServerSocket; 
import java.util.*;
import java.io.*;
public class Server {
	//Attributes
	private static ServerSocket Listener;
	private  Set<Integer> userNumbers= new HashSet<>();
	private  Set<ClientHandler> clientHandlers = new HashSet<>();
	private int port;
	private String serverAddress;
	//Methods
	 public Server(int port, String serverAddress) {
	        this.port = port;
	        this.serverAddress = serverAddress;
	    }
	
	public static void main(String[] args) throws Exception { 
		String serverAddress = "127.0.0.1"; 
		int serverPort = 5000;
		 Server server = new Server(serverPort, serverAddress);
	     server.execute();
	}
	 public void execute() {
			// Compteur incrémenté à chaque connexion d'un client au serveur 
			int clientNumber = 0;
			// Adresse et port du serveur
			// Création de la connexien pour communiquer ave les, clients

			try (ServerSocket Listener = new ServerSocket()){
				Listener.setReuseAddress(true);
				InetAddress serverIP = InetAddress.getByName(serverAddress);
				// Association de l'adresse et du port à la connexien
				Listener.bind(new InetSocketAddress(serverIP, this.port));
				System.out.format("The server is running on %s:%d%n", serverAddress, this.port); 
				System.out.format("Welcome to FBI van #1 server. This is a totally harmless server in the vicinity of your street."); 
				// À chaque fois qu'un nouveau client se, connecte, on exécute la fonstion
				// run() de l'objet ClientHandler 
				while (true) {
					// Important : la fonction accept() est bloquante: attend qu'un prochain client se  connecte
					// Une nouvetle connection : on incrémente le compteur clientNumber 
					ClientHandler newTarget=new ClientHandler(Listener.accept(), clientNumber++,this);
					clientHandlers.add(newTarget);
					addFBIClient(clientNumber);
					newTarget.start();
				}	
			} catch (IOException ex) {
	            System.out.println("Error in the server: " + ex.getMessage());
	            ex.printStackTrace();
	        }
	 }
	/**
	 * @summary Sees if the server has more than one user
	 * @author Alexandre Nguyen & Louis-Phlippe
	 * @version 1.0 Last modified on 20/05/2023
	 */ 
	
	 boolean hasMorethanOneUsers() {
	        return this.userNumbers.size()>1;
	    }
	
	/**
	 * @summary grabs the list of usernames
	 * @author Alexandre Nguyen & Louis-Phlippe
	 * @version 1.0 Last modified on 18/05/2023
	 */ 

	Set <Integer> getUserNumbers(){
		return this.userNumbers;
	}
	
	/**
	 * @summary grabs the list of usernames
	 * @author Alexandre Nguyen & Louis-Phlippe
	 * @version 1.0 Last modified on 18/05/2023
	 */ 
	
    public void communicateBetweenClients(String userInput, ClientHandler currentTarget) {
    	//loops through current users and if it's not the current user, broadcasts to the other users.
        for (ClientHandler fbiHandler : clientHandlers) {
            if (fbiHandler != currentTarget) {
            	fbiHandler.getWriter().println(userInput);
            }
       }
    }
	
	/**
	 * @summary Add usernumber integer to userNumbers set
	 * @author Alexandre Nguyen & Louis-Phlippe
	 * @version 1.0 Last modified on 18/05/2023
	 */ 
    public  void addFBIClient(Integer userID) {
		userNumbers.add(userID);
    }
	/**
	 * @summary Deletes user from clienthandler set and from userNumber set
	 * @author Alexandre Nguyen & Louis-Phlippe
	 * @version 1.0 Last modified on 18/05/2023
	 */ 
    public  void EliminateClient(Integer userID, ClientHandler clienthandler) {
	        boolean isEliminated = userNumbers.remove(userID);
	        if (isEliminated) {
	        	clientHandlers .remove(clienthandler);
	            System.out.println("The target " + userID + " has been swatted and has been forced to disconnect.");
	        }
}
}
