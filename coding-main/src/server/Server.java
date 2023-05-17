package server;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private int               port;
    private ServerSocket      serverSocket;

    public Server(int port) throws Exception{
        this.port         = port;
        this.serverSocket = new ServerSocket(port);
        System.out.println("Server runs on port " + port);
    }

    public void StartServer() {
        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("connected to socket: " + socket.getRemoteSocketAddress());

                ServerThread serverThread = new ServerThread(socket);
                serverThread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception{
        int    port   = 7777;
        Server server = new Server(port);
        server.StartServer();
    }
}
