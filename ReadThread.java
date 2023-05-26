/**
 * @summary The purpose of this class is to define a class which manages the inputs from the server
 * @author Alexandre Nguyen & Louis-Antoine Martel-Marquis
 * @version 6 Last modified on 26/05/2023
 * 
 */ 

import java.io.*;
import java.net.*;

public class ReadThread extends Thread{
	// ATTRIBUTES
	private DataInputStream input;
    private Socket socket;
    private Client client;

    // CONSTRUCTOR
    public ReadThread(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;
    }
    
    // METHODS
    public void run() {
	        while (true && !socket.isClosed()) {
	        	try {
		        		input = new DataInputStream(socket.getInputStream());
		        		if (input.available()>0) {
			    	    	String serverResponse=input.readUTF();
			    	    	System.out.println(serverResponse);
		        		}
	            } catch (IOException ex) {
	                System.out.println("Error reading from server: " + ex.getMessage());
	                ex.printStackTrace();
	                break;
	            }
	        }
	        try {
	        	input.close();
	        }catch (IOException ex) {
                System.out.println("Error closing ReadThread: " + ex.getMessage());
                ex.printStackTrace();
	        }
    }
}
