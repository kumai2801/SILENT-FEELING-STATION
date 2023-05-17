package client;

import java.net.Socket;

public class Client {
	private     String     host;
    private     int        port;
    private     Socket     socket;
    private     String     username;

    public Client(String host, int port, String username) throws Exception{
        this.host          = host;
        this.port          = port;
        this.socket        = new Socket(host, port);
        this.username      = username;
        System.out.println("Having connected to " + socket.getRemoteSocketAddress());
    }

    public void StartClient() {
        try {
            ClientThreadChat clientThreadChat = new ClientThreadChat(socket, username);
            clientThreadChat.start();
            
            ClientThreadContent clientThreadContent = new ClientThreadContent(socket, username);
            clientThreadContent.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception{
        Client client = new Client("localhost", 7777, "");
        client.StartClient();
    }
}
