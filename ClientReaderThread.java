import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;


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
					System.out.println(sender+ " wants to send you a file, respond to sender with \"ACCEPTFILE\" to accept and \"DECLINEFILE\" to decline");
					
				} else if(message.equals("DECLINEFILE")) {
					System.out.println(sender+ " declined your file");
					
				} else if(message.equals("ACCEPTFILE")) {
					System.out.println(sender + " accepted your file, setting up communication channel");
					PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true);
					ClientFileSenderThread fileServerThread = new ClientFileSenderThread(serverSocket, sender);
					new Thread(fileServerThread).start();
					out.println(sender);
					out.println("SOCKETINFO");
					out.println("127.0.0.1");
					out.println(5555);
				} else if(message.equals("SOCKETINFO")) {
					String ip = in.readLine();
					int port = Integer.parseInt(in.readLine());
					new Thread(new ClientFileRecieverThread(ip, port)).run();
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
