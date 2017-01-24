import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


public class ChatServer {
	public Map<String, Socket> socketList = new HashMap<String, Socket>();

	public ChatServer() {
		
		try {
			System.out.println(InetAddress.getLocalHost());
			ServerSocket serverSocket =  new ServerSocket(4444);
			System.out.println("Listening on port 4444");
			while(true) {
				Socket clientSocket = serverSocket.accept();	
				ServerThread serverThread = new ServerThread(clientSocket, socketList);
				new Thread(serverThread).start();
			}
		} catch(Exception e) { 
		}

	}
	public static void main(String[] args) {
		new ChatServer();
	}
}








