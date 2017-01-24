public class SynchronizedVariables {
	    private String fileName;
	    private int port;

	    public synchronized void setFileName(String newName) {
	        fileName = newName;
	    }
	    public synchronized void setPort(int port) {
	    	this.port = port;
	    }
	    public synchronized String getName() {
	        return fileName;
	    }
	    public synchronized int getPort() {
	    	return port;
	    }
	}