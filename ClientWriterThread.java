import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ClientWriterThread implements Runnable {
	Socket serverSocket;
	PrintWriter out;
	public ClientWriterThread(Socket serverSocket) {
		this.serverSocket = serverSocket;
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
			String fileName;
			while (true) {
				target = stdIn.readLine();
				message = stdIn.readLine();
				out.println(target);
				out.println(message);
				if(message.equals("SENDFILE")) {
					fileName = stdIn.readLine();
					out.println(fileName); //filename
					
				} else if(message.equals("ACCEPTFILE")) {
					fileName = stdIn.readLine();
					out.println(fileName); //filename
				}
			}

		} catch(Exception e) {
			System.out.println("Exception in WriterThread");
		}
	}
}


