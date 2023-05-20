package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientThreadChat extends Thread {

    public		static		ArrayList<ClientThreadChat>		clientList		= new ArrayList<>();
    private 	   			Socket 					    	client;
    private 	   			BufferedReader 			    	bufferedReader;
    private 	   			BufferedWriter 					bufferedWriter;
    private 	   			String 							nickName;

    public ClientThreadChat(Socket socket) {
        try {
            this.client = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.nickName = bufferedReader.readLine();
            clientList.add(this);
            getMessage("SERVER: " + nickName + " was join room chat!!!");
        } catch (Exception e) {
            doClose(socket, bufferedReader, bufferedWriter);
        }
    }

    public String getNickName() {
        return nickName;
    }

    public void getMessage(String massage) {
        for(ClientThreadChat eventHandler : clientList) {
            if (!eventHandler.nickName.equals(nickName)) {
                try {
                    eventHandler.bufferedWriter.write(massage + "\n");
                    eventHandler.bufferedWriter.flush();
                    
                } catch (Exception e) {
                    doClose(client, bufferedReader, bufferedWriter);
                }
            }
        }
    }

    public void doClose(Socket client, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        clientList.remove(this);
        getMessage("SERVER: " + nickName + " has left chat!!!");

        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }

            if (bufferedWriter != null) {
                bufferedWriter.close();
            }

            if (client != null) {
                client.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String massageClient;

        while (client.isConnected()) {
            try {
                massageClient = bufferedReader.readLine();
                getMessage(massageClient);
            } catch (Exception e) {
                doClose(client, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

}