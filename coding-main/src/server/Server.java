package server;

import java.net.ServerSocket;
import java.net.Socket;

import client.ClientThreadChat;

public class Server {
    private ServerSocket serverSocket;


    public Server(int port) throws Exception {
        serverSocket = new ServerSocket(port);
        System.out.println("Server runs on port " + port);
    }


    public void startServer() {
        try {
            while (!serverSocket.isClosed()) {
            	Socket socket = serverSocket.accept();
            	connectClientChat(socket);
            }
        } catch (Exception e) {
            closeServer();;
        }
    }
    
    public void connectClientChat(Socket socket) {
        ClientThreadChat clientThreadChat = new ClientThreadChat(socket);
        System.out.println(clientThreadChat.getNickName() + " has connected!!!");
        clientThreadChat.start(); 
    }


    public void closeServer() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws Exception {
        Server server = new Server(2801);
        server.startServer();
    }

}