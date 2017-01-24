import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;


public class ClientReaderThread implements Runnable{

	Socket serverSocket;
	BufferedReader in;
	SynchronizedVariables shared;
	int port;
	public ClientReaderThread(Socket serverSocket, SynchronizedVariables shared) {
		this.serverSocket = serverSocket;
		this.shared = shared;
		this.port = port;
	}

	public void run() {
		try {
			in =
					new BufferedReader(
							new InputStreamReader(serverSocket.getInputStream()));
			String message;
			String sender;
			while(true) {
				sender = in.readLine();
				message = in.readLine();
				if(message.equals("SENDFILE")) {
					System.out.println(sender+ " wants to send you a file named  Respond to sender with \"ACCEPTFILE\"  and \"DECLINEFILE\" to decline");
					
				} else if(message.equals("DECLINEFILE")) {
					System.out.println(sender+ " declined your file");
					
				} else if(message.equals("ACCEPTFILE")) {
					PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true);	
					ClientFileSenderThread fileServerThread = new ClientFileSenderThread(serverSocket, shared.getName(), shared.getPort());
					new Thread(fileServerThread).start();
					out.println(sender);
					out.println("SOCKETINFO");
					out.println(shared.getPort());
				} else if(message.equals("SOCKETINFO")) {
					String ip = in.readLine();
					System.out.println(ip);
					int port = Integer.parseInt(in.readLine());
					System.out.println(port);
					ClientFileRecieverThread fileClientThread = new ClientFileRecieverThread(ip, port);
					new Thread(fileClientThread).start();
					
					
					
					
				} else {
					System.out.println("From: " + sender + "\tMessage: " + message);
				}
			}

		} catch(IOException e) {
			System.out.println("Exception in ReaderThread");
			return;
		}
	}


}
