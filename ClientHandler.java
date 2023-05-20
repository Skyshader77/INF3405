/**
 * @summary The purpose of this class is to define a ClientHandler class
 * @author Alexandre Nguyen & Louis-Phlippe
 * @version 1.0 Last modified on 18/05/2023
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
	
	public ClientHandler(Socket socket, int clientNumber) {
		this.socket = socket; this.clientNumber = clientNumber;
		System.out.println("New connection with client#" + clientNumber + " at" + socket);
		}
	public void run() {   
		// Création de thread qui envoi un message à un client 
		try {
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			PassWordAuthentificationProtocol Pap=new PassWordAuthentificationProtocol();
			BufferedReader bufferedIn= new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String inputLine, outputLine;
			 // Initiate conversation with client
		    outputLine = Pap.processInput(null);
		    out.println(outputLine);
		    while ((inputLine = bufferedIn.readLine()) != null) {
		        outputLine = Pap.processInput(inputLine);
		        out.println(outputLine);
		        if (outputLine.equalsIgnoreCase("n"))
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
	//public PrintWriter getWriter() {
	        //return this.out;
	//}
	/**
	 * @summary returns the PassWordAuthentificationProtocol of the clientHandler class
	 * @author Alexandre Nguyen & Louis-Phlippe
	 * @version 1.0 Last modified on 19/05/2023
	 */ 
//	public PassWordAuthentificationProtocol getProtocol() {
//	        return this.Pap;
//	}

}
