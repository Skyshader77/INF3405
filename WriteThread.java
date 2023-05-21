/**
 * @summary The purpose of this class is to define a class which manages an user's outputs
 * @author Alexandre Nguyen & Louis-Phlippe
 * @version 1.0 Last modified on 20/05/2023
 */ 


import java.io.*;
import java.net.*;

public class WriteThread extends Thread {
    private Socket socket;
    private Client client;
    private PrintWriter writer;
    private BufferedReader inputReader;
    //private static final int AUTHENTICATED= 3;
    public WriteThread(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;
        try {
            writer = new PrintWriter(socket.getOutputStream(), true);
            inputReader = new BufferedReader(new InputStreamReader(System.in));
          //consoleReader= new BufferedReader(new InputStreamReader(System.in));
        } catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
 
    public void run() {
    	try {
//			out = new PrintWriter(socket.getOutputStream(), true);
//			PassWordAuthentificationProtocol Pap=new PassWordAuthentificationProtocol();
//			BufferedReader bufferedIn= new BufferedReader(new InputStreamReader(socket.getInputStream()));
//			String inputLine, outputLine;
//			 // Initiate conversation with client
//		    outputLine = Pap.processInput(null);
//		    out.println(outputLine);
//		    while ((inputLine = bufferedIn.readLine()) != null) {
//		        outputLine = Pap.processInput(inputLine);
//		        out.println(outputLine);
//		        if (outputLine.equalsIgnoreCase("n"))
//		            break;
//		    }
//	        //String clientLeft = clientNumber + " has been swatted and was forced to disconnect.";
//	        //server.communicateBetweenClients(clientLeft, this);
//		} catch (IOException e) {
//			System.out.println("Error handling client# " + clientNumber + ": " + e);
        String text;
        do {
            text = inputReader.readLine();
            writer.println(text);
        } while (!text.equals("bye"));
        socket.close();
        } catch (IOException ex) {
            System.out.println("Error writing to server: " + ex.getMessage());
        }
    }
}
