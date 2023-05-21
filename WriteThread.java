
/**
 * @summary The purpose of this class is to define a class which manages an user's outputs to the console
 * @author Alexandre Nguyen & Louis-Phlippe
 * @version 2.0 Last modified on 20/05/2023
 */ 


import java.io.*;
import java.net.*;

public class WriteThread extends Thread {
    private Socket socket;
    private Client client;
    private PrintWriter output;
    private BufferedReader inputReader;
    public WriteThread(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;
        try {
            output = new PrintWriter(socket.getOutputStream(), true);
            inputReader = new BufferedReader(new InputStreamReader(System.in));
        } catch (IOException ex) {
            System.out.println("Error getting output or input stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
 
    public void run() {
    	try {
    		String inputtext;
	        do {
	        	inputtext = inputReader.readLine();
	        	output.println(inputtext);
	        } while (!inputtext.equals("disconnect"));
	        output.close();
        } catch (IOException ex) {
            System.out.println("Error writing to server: " + ex.getMessage());
        }
    }
}
