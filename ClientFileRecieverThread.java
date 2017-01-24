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
			Socket fileServerSocket = new Socket("169.254.203.139", port);
			System.out.println("connected to fileserver");
			File newFile = new File("myfile2");
			InputStream is = fileServerSocket.getInputStream();
			DataInputStream dis = new DataInputStream(is);
			String fileName = dis.readUTF();
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
