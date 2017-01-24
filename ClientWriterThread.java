import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
public class ClientWriterThread implements Runnable {
	Socket serverSocket;
	PrintWriter out;
	
	SynchronizedVariables shared;
	int port;
	public ClientWriterThread(Socket serverSocket, SynchronizedVariables shared) {
		this.serverSocket = serverSocket;
		this.shared = shared;
	}

	public void run() {
		try {
			BufferedReader stdIn =
					new BufferedReader(
							new InputStreamReader(System.in));
			out =
					new PrintWriter(serverSocket.getOutputStream(), true);
			String target;
			String message;
			while (true) {
				target = stdIn.readLine();
				message = stdIn.readLine();
				if(message.equals("SENDFILE")) {
					String fileName = stdIn.readLine();
					int port = Integer.parseInt(stdIn.readLine());
					shared.setFileName(fileName);
					shared.setPort(port);
				}
				out.println(target);
				out.println(message);
				
			}
		} catch(Exception e) {
			System.out.println("Exception in WriterThread");
		}
	}
}


