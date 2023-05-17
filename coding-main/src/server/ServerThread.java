package server;

import java.io.IOException;
import java.net.Socket;

public class ServerThread extends Thread{
	private Socket socket;
	
	public ServerThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		while(!socket.isClosed()) {
			
		}
	}
	
	public void close() throws IOException {
		if(socket != null) {
			socket.close();
		}
	}
}
