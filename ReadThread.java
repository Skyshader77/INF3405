/**
 * @summary The purpose of this class is to define a class which manages an the inputs from the server
 * @author Alexandre Nguyen & Louis-Phlippe
 * @version 3.0 Last modified on 24/05/2023
 */ 

import java.io.*;
import java.net.*;

public class ReadThread extends Thread{
	
	//ATTRIBUTES
	private BufferedReader input;
    private Socket socket;
    private Client client;
  //METHODS
    
    public ReadThread(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;
    }
    public void run() {
	        while (true && !socket.isClosed()) {
	        	try {
	        		input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    	    	String serverResponse=input.readLine();
	    	    	System.out.println("\n" + serverResponse);
	            } catch (IOException ex) {
	                System.out.println("Error reading from server: " + ex.getMessage());
	                ex.printStackTrace();
	                break;
	            }
	        }

    }
}
