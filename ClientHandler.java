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
	private Server server;
	private int clientNumber;
	private PrintWriter out;
	private String username;
	private static final int AUTHENTICATED= 3;
	public ClientHandler(Socket socket, int clientNumber, Server server) {
		this.socket = socket; this.clientNumber = clientNumber;this.server=server;
		System.out.println("New connection with client#" + clientNumber + " at" + socket);
		}
	public void run() {   
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			PassWordAuthentificationProtocol Pap=new PassWordAuthentificationProtocol();
			BufferedReader bufferedIn= new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String inputLine, outputLine;
			 // Initiate conversation with client
		    outputLine = Pap.processInput(null);
		    out.println(outputLine);
		    //while the user has not been authenticated
		    while (Pap.getState()<AUTHENTICATED) {
		    	inputLine = bufferedIn.readLine();
		        outputLine = Pap.processInput(inputLine);
		        out.println(outputLine);
		    }
		    username=Pap.getUser();
		    //while the user has not been disconnected
		    while ((inputLine = bufferedIn.readLine()) != null) {
		    	String formattedOutput=username+inputLine;
		        out.println(formattedOutput);
		        server.communicateBetweenClients(formattedOutput, this);
		        if (inputLine.equalsIgnoreCase("disconnect"))
		            break;
		    }
		} catch (IOException e) {
			System.out.println("Error handling client# " + clientNumber + ": " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				//deletes the user from the server
			    server.EliminateClient(clientNumber, this);
		        String clientLeft =username + " has been swatted and was forced to disconnect.";
		        server.communicateBetweenClients(clientLeft, this);
		        socket.close();
			} catch (IOException e) {
				System.out.println("Couldn't close a socket, what's going on?" + e.getMessage());
	            e.printStackTrace();
			}
				System.out.println("Connection with " + username + " closed");
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

}
