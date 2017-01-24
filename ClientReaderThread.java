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
	public ClientReaderThread(Socket serverSocket) {
		this.serverSocket = serverSocket;

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
					String fileName = in.readLine();
					System.out.println(sender+ " wants to send you a file named " +fileName +  "Respond to sender with \"ACCEPTFILE\"  following the filename to accept and \"DECLINEFILE\" to decline");
					
				} else if(message.equals("DECLINEFILE")) {
					System.out.println(sender+ " declined your file");
					
				} else if(message.equals("ACCEPTFILE")) {
					String fileName = in.readLine();
					PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true);
					
					ClientFileSenderThread fileServerThread = new ClientFileSenderThread(serverSocket, fileName);
					new Thread(fileServerThread).start();
					out.println(sender);
					out.println("SOCKETINFO");
					out.println(5555);
				} else if(message.equals("SOCKETINFO")) {
					System.out.println("socketinfo kom");
					String ip = in.readLine();
					System.out.println(ip);
					int port = Integer.parseInt(in.readLine());
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
