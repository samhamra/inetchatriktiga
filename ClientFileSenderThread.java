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
	String fileName;
	
	public ClientFileSenderThread(Socket serverSocket, String fileName) {
		this.serverSocket = serverSocket;
		this.fileName = fileName;
	}


	public void run() {
		try {		
			ServerSocket fileSocket = new ServerSocket(PORT);
			Socket clientSocket = fileSocket.accept();
			System.out.println("fileclient connected");
			System.out.println(fileName);
			File myFile = new File(fileName);
			FileInputStream in = new FileInputStream(myFile);
			OutputStream out = clientSocket.getOutputStream();
			DataOutputStream dout = new DataOutputStream(out);
	
			dout.writeUTF(fileName);
			
			long fileSize = myFile.length();
			System.out.println(fileSize);
			dout.writeLong(fileSize);
			
			byte[] bytes = new byte[8192];
			long sent = 0;
			while(sent < fileSize) {
				long numThisTime = fileSize - sent;

				numThisTime = numThisTime < bytes.length ? numThisTime : bytes.length;

				int numRead = in.read(bytes, 0, (int) numThisTime);

				if(numRead ==-1 ) break;

				dout.write(bytes,0,numRead);

				sent += numRead;
			}
			dout.flush();
			in.close();
			dout.close();
			System.out.println("File transferred");

		} catch (IOException e) {

		}
	}

}
