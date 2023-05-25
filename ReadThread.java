/**
 * @summary The purpose of this class is to define a class which manages an the inputs from the server
 * @author Alexandre Nguyen & Louis-Phlippe
 * @version 4.0 Last modified on 25/05/2023
 */ 

import java.io.*;
import java.net.*;

public class ReadThread extends Thread{
	
	//ATTRIBUTES
	private DataInputStream input;
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
	        		input = new DataInputStream(socket.getInputStream());
	    	    	String serverResponse=input.readUTF();
	    	    	System.out.println(serverResponse);
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
