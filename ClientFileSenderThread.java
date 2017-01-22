import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class ClientFileSenderThread implements Runnable {
	private Socket serverSocket;
	final int PORT = 5555;
	private String target;
	private String filePath;
	public ClientFileSenderThread(Socket serverSocket, String target) {
		this.serverSocket = serverSocket;
		this.target = target;
	}


	public void run() {
		try {
			ServerSocket fileSocket = new ServerSocket(PORT);
			PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true);
			Socket clientSocket = fileSocket.accept();
			System.out.println(InetAddress.getLocalHost());
			out.println(target);
			out.println("SOCKETINFO");
			out.println(PORT);

			File myFile = new File(filePath);
			byte[] byteArray = new byte[(int) myFile.length()];
			FileInputStream fis = new FileInputStream(myFile);
			BufferedInputStream bis = new BufferedInputStream(fis);
			bis.read(byteArray, 0, byteArray.length);
			OutputStream os = clientSocket.getOutputStream();

			DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
			System.out.println(myFile.length());
			dos.writeLong(myFile.length());


			os.write(byteArray, 0, byteArray.length);
			os.flush();
			fileSocket.close();

		} catch (IOException e) {

		}
	}

}
