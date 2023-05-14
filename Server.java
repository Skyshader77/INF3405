import java.net.InetAddress; 
import java.net.InetSocketAddress; 
import java.net.ServerSocket; 
public class Server {
	private static ServerSocket Listener;
	public static void main(String[] args) throws Exception { 
		// Compteur incrémenté à chaque connexion d'un client au serveur 
		int clientNumber = 0;
		// Adresse et port du serveur
		String serverAddress = "127.0.0.1"; 
		int serverPort = 5000;
		// Création de la connexien pour communiquer ave les, clients
		Listener = new ServerSocket();
		Listener.setReuseAddress(true);
		InetAddress serverIP = InetAddress.getByName(serverAddress);
		// Association de l'adresse et du port à la connexien
		Listener.bind(new InetSocketAddress(serverIP, serverPort));
		System.out.format("The server is running on %s:%d%n", serverAddress, serverPort); 
		System.out.format("Welcome to FBI van #1 server. This is a totally harmless server in the vicinity of your street."); 
		try {
			
			// À chaque fois qu'un nouveau client se, connecte, on exécute la fonstion
			// run() de l'objet ClientHandler 
			while (true) {
				// Important : la fonction accept() est bloquante: attend qu'un prochain client se  connecte
				// Une nouvetle connection : on incrémente le compteur clientNumber 
				new ClientHandler(Listener.accept(), clientNumber++).start();
			}	
		} finally {
			// Fermeture de la connexion
				Listener.close(); 
		}
	}
}

