package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket 		   socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String 		   nickName;

    public Client(String localhost, int port, String nickName) {
        try {
            this.socket		    = new Socket(localhost, port);
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.nickName 		= nickName;

        } catch (Exception e) {
            doClose(socket, bufferedReader, bufferedWriter);;
        }
    }

    public void doSend() {
        try {
            bufferedWriter.write(nickName + "\n");
            bufferedWriter.flush();

            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()) {
                String send = scanner.nextLine();
                bufferedWriter.write(nickName + ": " + send + "\n");
                bufferedWriter.flush();
            }
        } catch (Exception e) {
            doClose(socket, bufferedReader, bufferedWriter);;
        }
    }

    public void doReceive() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                String chat;

                while (socket.isConnected()) {
                    try {
                        chat = bufferedReader.readLine();
                        System.out.println(chat);
                    } catch (Exception e) {
                        doClose(socket, bufferedReader, bufferedWriter);;
                    }
                }
            }
        }).start();
    }
    
    public void doClose(Socket client, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
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

    public static void main(String[] args) throws Exception{
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input your name: ");
        String name = scanner.nextLine();
        
        Client client = new Client("localhost", 2801, name);
        client.doReceive();
        client.doSend();
    }
}
