import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;



public class ClientFileRecieverThread implements Runnable {
	String ip;
	int port;


	public ClientFileRecieverThread(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}


	public void run() {
		try {
			String realIp = ip.replace("/", "");
			Socket fileServerSocket = new Socket(realIp, port);
			System.out.println("connected to: "+ realIp + " on port " + port);
			InputStream is = fileServerSocket.getInputStream();
			System.out.println(1);
			DataInputStream dis = new DataInputStream(is);
			String fileName = dis.readUTF();
			File newFile = new File(fileName);
			FileOutputStream fos = new FileOutputStream(newFile);
			BufferedOutputStream bos = new BufferedOutputStream(fos);			
			long fileSize = dis.readLong();			
			byte[] bytes = new byte[8192];
			long read = 0;

			while(read < fileSize) {
				long toRead = fileSize - read;
				toRead = toRead < bytes.length ? toRead : bytes.length;
				int numRead = dis.read(bytes, 0, (int) toRead);
				if(numRead ==-1 ) break;
				bos.write(bytes,0,numRead);
				read += numRead;
			}
			bos.close();
			dis.close();
			is.close();
			System.out.println("File recieved");
		} catch (IOException e) {

		}
	}

}
