/**
 * @summary The purpose of this class is to define a ClientHandler class that 
 * accounts for every client authentificated, as well as saves the messages
 * each user writes in the chat and displays them in the right format.
 * @author Alexandre Nguyen & Louis-Antoine Martel-Marquis
 * @version 4.0 Last modified on 25/05/2023
 * 
 */ 

import java.io.*;
import java.util.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClientHandler extends Thread{
	// ATTRIBUTES
	private Socket socket;
	private Server server;
	private int clientNumber;
	private DataOutputStream out;
	private String username;
	private static final int AUTHENTICATED= 3;
	
	// CONSTRUCTOR
	public ClientHandler(Socket socket, int clientNumber, Server server) {
		this.socket = socket; this.clientNumber = clientNumber;this.server=server;
		System.out.println("New connection with client#" + clientNumber + " at" + socket);
		}
	
	// METHODS
	public void run() {   
		try {
			out = new DataOutputStream(socket.getOutputStream());
			PassWordAuthentificationProtocol Pap=new PassWordAuthentificationProtocol();
			DataInputStream bufferedIn= new DataInputStream(socket.getInputStream());
			String inputLine, outputLine;
			
			// Initiate conversation with client
		    outputLine = Pap.processInput("Start Authentication");
		    out.writeUTF(outputLine);
		    
		    //while the user has not been authenticated...
		    while (Pap.getState()<AUTHENTICATED) {
		    	// initiate the password authentification protocol
		    	inputLine = bufferedIn.readUTF();
		        outputLine = Pap.processInput(inputLine);
		        out.writeUTF(outputLine);
		    }
		    
		    username=Pap.getUser();
		    
		    printLastMessages(out); // display previous messages
		    
		    //while the user has not been disconnected ...
		    while ((inputLine = bufferedIn.readUTF()) != null) {
		    	//disconnect the user if they write "disconnect" in the chat
		    	if (inputLine.equalsIgnoreCase("disconnect")) {
		    		socket.close();
		    		server.EliminateClient(clientNumber, this);
			        String clientLeft =username + " has been swatted and was forced to disconnect.";
			        server.communicateBetweenClients(clientLeft, this);
		            break;
		    	}
		    
		    	// Save the user's input
		    	String formattedOutput=formatClientInput(inputLine);
		        out.writeUTF(formattedOutput);
		        saveClientInputTXT(formattedOutput);
		        server.communicateBetweenClients(formattedOutput, this);
		    }
		} catch (IOException e) {
			System.out.println("Error handling client# " + clientNumber + ": " + e.getMessage());
			e.printStackTrace();
		} 
	}
	
	/**
	 * @summary returns the printwriter of the clientHandler class
	 * @return the DataOutputStream of the instance of this class. 
	 * @author Alexandre Nguyen & Louis-Antoine Martel-Marquis
	 * @version 1.0 Last modified on 19/05/2023
	 * 
	 */ 
	public DataOutputStream getWriter() {
	        return this.out;
	}

	/**
     * @summary The purpose of this function is arrange the user's input
     * into the desired format which will be saved and displayed by the
     * console. 
     * @return a String of the user's input in the right format
     * @author Alexandre Nguyen & Louis-Antoine Martel-Marquis
     * @version 1.0 Last modified on 17/05/2023
     * 
     */ 
    public String formatClientInput(String theInput) {
    	LocalDateTime now = LocalDateTime.now();

    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd@HH:mm:ss");
        String formattedDateTime = now.format(formatter);

    	return "[" + username + " - " + this.server.getServerAddress() + ":" + this.server.getPort() + " - " + formattedDateTime + "]: " + theInput;
    }

    /**
     * @summary The purpose of this function is to save any user's input into a .txt file 
     * @author Alexandre Nguyen & Louis-Antoine Martel-Marquis
     * @version 2.0 Last modified on 15/05/2023
     * 
     */ 
    public void saveClientInputTXT(String theInput) throws IOException{
		BufferedWriter writer = new BufferedWriter(new FileWriter("NSA_Spy_File.txt", true));

		writer.write(theInput);
		writer.newLine();
		writer.close();
	}

    /**
     * @summary The purpose of this function is to read the last 15 messages in the server and print them to the user.
     * @author Alexandre Nguyen & Louis-Antoine Martel-Marquis
     * @version 3.0 Last modified on 25/05/2023
     * 
     */ 
    public void printLastMessages(DataOutputStream out) {
        try {        	
        	StringBuilder printToConsole=new StringBuilder();;
        	String newLine = System.getProperty("line.separator");
        	
        	//Reads from file
        	File f = new File("NSA_Spy_File.txt");
        	if (!f.exists()) {
        		BufferedWriter fbiLeak = new BufferedWriter(new FileWriter("NSA_Spy_File.txt"));
        	}
        	
        	BufferedReader reader = new BufferedReader(new FileReader("NSA_Spy_File.txt"));
            Deque<String> messages = new ArrayDeque<String>();
            String line;

            while ((line = reader.readLine()) != null) {
                messages.add(line);
            }
            
           int sizeofDeque=messages.size();
           
           //Removes excess messages
           while (sizeofDeque > 15){
        	   		messages.removeFirst();
        	   		sizeofDeque=messages.size();
           }
           
           //Prints to the user
           Iterator<String> message = messages.iterator();
           out.writeUTF("Your last available message(s) is/are :"+newLine);
           
           while (message.hasNext()) {
        	   String theOutput=message.next();
        	   printToConsole.append(theOutput+newLine);
            }
           
           out.writeUTF(printToConsole.toString());
    	   out.flush();
    	   
           reader.close();
        } catch (IOException e) {
            System.out.println("Failed to read the chat messages from the file.");
        }
    }  
}
