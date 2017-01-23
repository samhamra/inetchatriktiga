import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
public class ChatClient {
	PrintWriter out;
	BufferedReader in;
	public static void main(String[] args) {
		new ChatClient(args[0]);
	}
	public ChatClient(String nickname) {
		try {
			System.out.println("Connecting to server...");
			Socket serverSocket = new Socket("localhost", 4444);
			System.out.println("Welcome " + nickname);
			out = new PrintWriter(serverSocket.getOutputStream(), true);
			out.println(nickname);
			ClientWriterThread writerThread = new ClientWriterThread(serverSocket);
			ClientReaderThread readerThread = new ClientReaderThread(serverSocket);
			new Thread(writerThread).start();
			new Thread(readerThread).start();
		} catch(Exception E) {
			System.out.println("Exception in ChatClient");
		}
	}

}
