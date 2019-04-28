package com.hangman;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.lang.ClassNotFoundException;
import java.net.Socket;
import java.util.Random;

public class Server {
    //socket server port on which it will listen
    private static int PORT = 8888;
    //static ServerSocket variable
    private static ServerSocket server;

    public static void main(String[] args) throws ClassNotFoundException {
        int i =0;
        Thread thread = null;
        try {
            //Create ServerSocket
            server = new ServerSocket(PORT);
            while (true) {
                System.out.println("Waiting for the client request");
                Socket socket = server.accept();
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                String message = (String) ois.readObject();
                System.out.println("Message Client Received: " + message);
                Random rand = new Random();
                //Generate Thread and port
                int newPort = rand.nextInt(9000)+1000;
                MultiThreadRespond multiThreadRespond = new MultiThreadRespond(newPort);
                thread = new Thread(multiThreadRespond);
                thread.start();
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(""+newPort);

                ois.close();
                oos.close();
                socket.close();
                i++;
            }
        }
        catch (IOException ex) {
            try {
                server.close();
            } catch (IOException e) {
                System.err.println("ERROR closing socket: " + e.getMessage());
            }
        }
    }
}
