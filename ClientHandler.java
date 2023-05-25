/**
 * @summary The purpose of this class is to define a ClientHandler class
 * @author Alexandre Nguyen & Louis-Phlippe
 * @version 4.0 Last modified on 21/05/2023
 */ 


import java.io. PrintWriter;
import java.io.DataInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Set;
import java.net.ServerSocket; 
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
		    
		    printLastMessages(out);
		    
		    //while the user has not been disconnected
		    while ((inputLine = bufferedIn.readLine()) != null) {
		    		
	    	 	if (inputLine.length() <= 200) { 
	    			try {
				    	String formattedOutput = formatClientInput(inputLine);
				        out.println(formattedOutput);
				        saveClientInputTXT(formattedOutput);
				        server.communicateBetweenClients(formattedOutput, this);
				        if (inputLine.equalsIgnoreCase("disconnect")) {
				        	break;
				        }
	    			} catch (IOException e) {
	    					System.out.println("Program failed to write the input to a text file.");
	    			}
	    		}
	    		else {
	    			out.println("Warning: Input exceeds 200 characters. It will not be saved.");
	    		}
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

    /**
     * @summary The purpose of this function is to read the username/password combinations from a TXT file
     * @return a Boolean saying if the operation succeeded or not
     * @author Alexandre Nguyen & Louis-Antoine
     * @version 2.0 Last modified on 15/05/2023
     * 
     */ 
    public String formatClientInput(String theInput) {
    	LocalDateTime now = LocalDateTime.now();
    	
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd@HH:mm:ss");
        String formattedDateTime = now.format(formatter);
            
    	return "[" + username + " - " + this.server.getServerAddress() + ":" + this.server.getPort() + " - " + formattedDateTime + "]: " + theInput;
    }
    
    /**
     * @summary The purpose of this function is to read the username/password combinations from a TXT file
     * @return a Boolean saying if the operation succeeded or not
     * @author Alexandre Nguyen & Louis-Antoine
     * @version 2.0 Last modified on 15/05/2023
     * 
     */ 
    public void saveClientInputTXT(String theInput) throws IOException{
		BufferedWriter writer = new BufferedWriter(new FileWriter("clientChat.txt", true));
		
		writer.write(theInput);
		writer.newLine();
		writer.close();
	}
    
    /**
     * @summary The purpose of this function is to read the username/password combinations from a TXT file
     * @return a Boolean saying if the operation succeeded or not
     * @author Alexandre Nguyen & Louis-Antoine
     * @version 2.0 Last modified on 15/05/2023
     * 

    public void printLastMessages() {
        try {
        	BufferedReader reader = new BufferedReader(new FileReader("clientChat.txt"));
            LinkedList<String> messages = new LinkedList<>();
            String line;
            
            while ((line = reader.readLine()) != null) {
                messages.add(line);
            }
            
           while (messages.size() > 15){
           			messages.removeFirst();
           }
           
           for (String message : messages) {
                System.out.println(message);
            }
            
            reader.close();
            
        } catch (IOException e) {
            System.out.println("Failed to read the chat messages from the file.");
        }
    } 
    */ 
    /**
     * @summary The purpose of this function is to read the username/password combinations from a TXT file
     * @return a Boolean saying if the operation succeeded or not
     * @author Alexandre Nguyen & Louis-Antoine
     * @version 2.0 Last modified on 15/05/2023
     * 
*/ 
    public void printLastMessages(PrintWriter out) {
        try {
        	BufferedReader reader = new BufferedReader(new FileReader("clientChat.txt"));
            LinkedList<String> messages = new LinkedList<>();
            String line;
            
            while ((line = reader.readLine()) != null) {
                messages.add(line);
            }
            
           while (messages.size() > 15){
           			messages.removeFirst();
           }
           
           out.println("Your last available message(s) is/are : ");
           for (String message : messages) {
                out.println(message);
            }
            
            reader.close();
            
        } catch (IOException e) {
            System.out.println("Failed to read the chat messages from the file.");
        }
    }  
}
