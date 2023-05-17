import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class ClientHandler extends Thread{
	private Socket socket;
	private int clientNumber;
	public ClientHandler(Socket socket, int clientNumber) {
		this.socket = socket;
		this.clientNumber = clientNumber;
		System.out.println("New connection with client#" + clientNumber + " at " + socket);
		}
	public void run() {   
		BufferedWriter fileWriter = null;
		
		// Création de thread qui envoie un message à un client 
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			DataOutputStream out = new DataOutputStream(socket.getOutputStream()); 
			
			String inputLine;
			
			fileWriter = new BufferedWriter(new FileWriter("client" + clientNumber + ".txt"));
			
			while((inputLine = in.readLine()) != null){
                
				if (inputLine.length() > 200) {
                    inputLine = inputLine.substring(0, 200);
                }
				
				System.out.println("[ Client #" + clientNumber + "]: " +  inputLine + "\n");
				
                fileWriter.write(inputLine);
                fileWriter.newLine();
                fileWriter.flush();
				
				out.writeUTF("Server received:" + inputLine + "\n");
			}
			
            in.close();
            out.close();

		} catch (IOException e) {
			System.out.println("Error handling client# " + clientNumber + ": " + e);
		} finally {
			try {
                if (fileWriter != null) {
                    fileWriter.close();
                }
				socket.close();
			} catch (IOException e) {
				System.out.println("Couldn't close a socket, what's going on?");
			}
				System.out.println("Connection with client# " + clientNumber + " closed");
		}
	}
}

