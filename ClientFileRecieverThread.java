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
		Socket fileServerSocket = new Socket(ip, port);
		BufferedReader in =
				new BufferedReader(
						new InputStreamReader(fileServerSocket.getInputStream()));
		byte[] mybytearray = new byte[1024];
	  //  InputStream is = fileServerSocket.getInputStream();
	    FileOutputStream fos = new FileOutputStream("s.pdf");
	    BufferedOutputStream bos = new BufferedOutputStream(fos);
	   // int bytesRead = is.read(mybytearray, 0, mybytearray.length);
	 //   bos.write(mybytearray, 0, bytesRead);
	    bos.close();
	 //   sock.close();
		} catch (IOException e) {
			
		}
	}

}
