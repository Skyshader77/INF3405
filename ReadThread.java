/**
 * @summary The purpose of this class is to define a class which manages an the inputs from the server
 * @author Alexandre Nguyen & Louis-Phlippe
 * @version 1.0 Last modified on 20/05/2023
 */ 

import java.io.*;
import java.net.*;

public class ReadThread extends Thread{
	private BufferedReader reader;
    private Socket socket;
    private Client client;
 
    public ReadThread(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;
    }
    public void run() {
	        while (true) {
	        	try {
	        		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    	    	String response=reader.readLine();
	    	    	System.out.println("\n" + response);
	            } catch (IOException ex) {
	                System.out.println("Error reading from server: " + ex.getMessage());
	                ex.printStackTrace();
	                break;
	            }
	        }

    }
}
