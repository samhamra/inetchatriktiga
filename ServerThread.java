import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;


public class ServerThread implements Runnable {
	Socket clientSocket;
	PrintWriter out;
	PrintWriter clientOut;
	BufferedReader in;
	Map<String, Socket> socketList;
	String nickname;
	ChatServer chatServer
	public ServerThread(Socket client, Map<String, Socket> socketList, ChatServer chatServer) {
		this.clientSocket = client;
		this.socketList = socketList;
		this.chatServer = chatServer;
	}

	public void run() {
		try {
			in = new BufferedReader(
					new InputStreamReader(clientSocket.getInputStream()));
			System.out.println("New connection:" + clientSocket.getRemoteSocketAddress());
			nickname = in.readLine();

			socketList.put(nickname, clientSocket);
		} catch(IOException e) {

		}

		String target;
		String message;
		Socket targetSocket;
		while (true) {
			try { 
				target = in.readLine();
				message = in.readLine();
				if(target.equals("")) {
					chatServer.broadcast(message, nickname, target, true);
					System.out.println("Message delivered to everyone");
				} else if(socketList.containsKey(target)) {	

					targetSocket = socketList.get(target);
					out = new PrintWriter(targetSocket.getOutputStream(), true);
					
					if(message.equals("SENDFILE")) {
						chatServer.broadcast(message, nickname, target, false)
					} else	if(message.equals("ACCEPTFILE")) {
						chatServer.broadcast(message, nickname, target, false);;
					} else	if(message.equals("SOCKETINFO")) {
						out.println(nickname);
						out.println(message);
						out.println(socketList.get(nickname).getRemoteSocketAddress());
						String port = in.readLine();
						out.println(port); //port

					} else {
						chatServer.broadcast(message, nickname, target, false);
						System.out.println("Message delivered to:" + target);
					}
				}

			} catch(Exception e) {
				System.out.println("Exception in ServerThread");
			}
		}

	}
}
