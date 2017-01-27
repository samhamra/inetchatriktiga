import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.io.PrintWriter;


public class ChatServer {
	public Map<String, Socket> socketList = new HashMap<String, Socket>();

	public ChatServer() {
		
		try {	
			ServerSocket serverSocket =  new ServerSocket(4444);
			System.out.println("www.samhamra.com");
			System.out.println("Listening on port 4444");
			while(true) {
				Socket clientSocket = serverSocket.accept();	
				ServerThread serverThread = new ServerThread(clientSocket, socketList, this);
				new Thread(serverThread).start();
			}
		} catch(Exception e) {
			System.out.println(e);
		}

	}

	public synchronized void broadcast(String message, String nickname) throws Exception {
	PrintWriter out;

	for (Map.Entry<String, Socket> entry : socketList.entrySet()) {
						out = new PrintWriter(entry.getValue().getOutputStream(), true);
					out.println(nickname);
						out.println(message);
						//out.println("From: " + nickname + "\tMessage: " + message);


}	
	
}
	
	public static void main(String[] args) {
		new ChatServer();
	}
}








