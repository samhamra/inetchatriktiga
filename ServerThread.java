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
	public ServerThread(Socket client, Map<String, Socket> socketList) {
		this.clientSocket = client;
		this.socketList = socketList;
	}
	public void run() {
		try {
			in = new BufferedReader(
					new InputStreamReader(clientSocket.getInputStream()));
			System.out.println("New connection:" + clientSocket.getLocalAddress());
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
					for (Map.Entry<String, Socket> entry : socketList.entrySet()) {
						out = new PrintWriter(entry.getValue().getOutputStream(), true);
						out.println(nickname);
						out.println(message);
						//out.println("From: " + nickname + "\tMessage: " + message);

					}
					System.out.println("Message delivered to everyone");
				} else if(socketList.containsKey(target)) {	

					targetSocket = socketList.get(target);
					out = new PrintWriter(targetSocket.getOutputStream(), true);

					if(message.equals("SOCKETINFO")) {
						String targetIp = socketList.get(target).getInetAddress().getHostAddress();
						out.println(nickname);
						out.println(targetIp);
						out.println(message); //port

					} else {
						out.println(nickname);
						out.println(message);
						//out.println("From: " + nickname + "\tMessage: " + message);
						System.out.println("Message delivered to:" + target);
					}
				}

			} catch(Exception e) {
				System.out.println("Exception in ServerThread");
			}
		}

	}
}
