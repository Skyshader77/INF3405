import java.io. PrintWriter;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler extends Thread{
	private Socket socket;
	private int clientNumber;
	public ClientHandler(Socket socket, int clientNumber) {
		this.socket = socket; this.clientNumber = clientNumber;
		System.out.println("New connection with client#" + clientNumber + " at" + socket);
		}
	public void run() {   
		// Création de thread qui envoi un message à un client 
		try {
			PrintWriter out = new  PrintWriter(socket.getOutputStream(),true); 
			BufferedReader bufferedIn= new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String inputLine, outputLine;
			 // Initiate conversation with client
		    PassWordAuthentificationProtocol Pap = new PassWordAuthentificationProtocol();
		    outputLine = Pap.processInput(null);
		    out.println(outputLine);
			
		    while ((inputLine = bufferedIn.readLine()) != null) {
		        outputLine = Pap.processInput(inputLine);
		        out.println(outputLine);
		        if (outputLine.equals("Bye."))
		            break;
		    }   
		    
		// création de canal d’envoi out.writeUTF("Hello from server - you are client#" + clientNumber); 
		// envoi de message
		} catch (IOException e) {
			System.out.println("Error handling client# " + clientNumber + ": " + e);
		} finally {
		try {
			socket.close();
		} catch (IOException e) {
			System.out.println("Couldn't close a socket, what's going on?");
		}
			System.out.println("Connection with client# " + clientNumber + " closed");
		}
		}

}
