/**
 * @summary The purpose of this class is to define a class which manages an user's outputs to the console
 * @author Alexandre Nguyen & Louis-Antoine Martel-Marquis
 * @version 5 Last modified on 26/05/2023
 * 
 */ 

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class WriteThread extends Thread {
	//ATTRIBUTES
    private Socket socket;
    private Client client;
    private Scanner sc;
    private DataOutputStream output;
    
    //METHODS
    public WriteThread(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;
        try {
            output = new DataOutputStream(socket.getOutputStream());
            sc = new Scanner(System.in);
        } catch (IOException ex) {
            System.out.println("Error getting output or input stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
 
    public void run() {
    	try {
    		
    		String inputtext;
    		
    		//while the user has not disconnected
	        do {
	        	inputtext = sc.nextLine();
	        	output.writeUTF(inputtext);
	        } while (!inputtext.equals("disconnect"));
	        
	        try {
	        	output.close();
	            socket.close();
	        } catch (IOException ex) {
	 
	            System.out.println("Error writing to server: " + ex.getMessage());
	        }
	        
        } catch (IOException ex) {
            System.out.println("Error writing to server: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
