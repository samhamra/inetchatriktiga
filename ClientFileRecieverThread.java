import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.omg.CORBA.portable.InputStream;


public class ClientFileRecieverThread implements Runnable {
	String ip;
	int port;
	
	
	public ClientFileRecieverThread(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}


	public void run() {
		try {
			System.out.println("hejsan filereciever");
		Socket fileServerSocket = new Socket("localhost", port);
		System.out.println("connected to fileserver");
		BufferedReader in =
				new BufferedReader(
						new InputStreamReader(fileServerSocket.getInputStream()));
		byte[] mybytearray = new byte[1024];
		
	    InputStream is = (InputStream) fileServerSocket.getInputStream();
	    FileOutputStream fos = new FileOutputStream("newfile");
	    BufferedOutputStream bos = new BufferedOutputStream(fos);
	    int fileSize = is.read_long();
	    byte[] byteArray = new byte[fileSize];
	    int bytesRead = is.read(mybytearray, 0, fileSize);
	    bos.write(mybytearray, 0, bytesRead);
	    bos.close();
		} catch (IOException e) {
			
		}
	}

}
