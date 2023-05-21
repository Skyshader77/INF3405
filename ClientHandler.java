/**
 * @summary The purpose of this class is to define a ClientHandler class
 * @author Alexandre Nguyen & Louis-Phlippe
 * @version 2.0 Last modified on 20/05/2023
 */ 


import java.io. PrintWriter;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.Set;
import java.net.ServerSocket; 

public class ClientHandler extends Thread{
	private Socket socket;
	private int clientNumber;
	private PrintWriter out;
	private String username;
	private static final int AUTHENTICATED= 3;
	public ClientHandler(Socket socket, int clientNumber, Server server) {
		this.socket = socket; this.clientNumber = clientNumber;
		System.out.println("New connection with client#" + clientNumber + " at" + socket);
		}
	public void run() {   
		// Création de thread qui envoi un message à un client 
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			PassWordAuthentificationProtocol Pap=new PassWordAuthentificationProtocol();
			BufferedReader bufferedIn= new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String inputLine, outputLine;
			 // Initiate conversation with client
		    outputLine = Pap.processInput(null);
		    out.println(outputLine);
		    while (Pap.getState()<AUTHENTICATED) {
		    	inputLine = bufferedIn.readLine();
		        outputLine = Pap.processInput(inputLine);
		        out.println(outputLine);
		    }
		    username=Pap.getUser();
		    while ((inputLine = bufferedIn.readLine()) != null) {
		        out.println(username+inputLine);
		        if (inputLine.equalsIgnoreCase("bye"))
		            break;
		    }
	        //String clientLeft = clientNumber + " has been swatted and was forced to disconnect.";
	        //server.communicateBetweenClients(clientLeft, this);
		} catch (IOException e) {
			System.out.println("Error handling client# " + clientNumber + ": " + e);
		} finally {
		try {
			//server.EliminateClient(clientNumber, this);
			socket.close();
		} catch (IOException e) {
			System.out.println("Couldn't close a socket, what's going on?");
		}
			System.out.println("Connection with client# " + clientNumber + " closed");
		}
		}
	/**
	 * @summary returns the printwriter of the clientHandler class
	 * @author Alexandre Nguyen & Louis-Phlippe
	 * @version 1.0 Last modified on 19/05/2023
	 */ 
	public PrintWriter getWriter() {
	        return this.out;
	}
	/**
	 * @summary returns the PassWordAuthentificationProtocol of the clientHandler class
	 * @author Alexandre Nguyen & Louis-Phlippe
	 * @version 1.0 Last modified on 19/05/2023
	 */ 
//	public PassWordAuthentificationProtocol getProtocol() {
//	        return this.Pap;
//	}

}
