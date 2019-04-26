package com.hangman;

import java.io.IOException;
import java.net.ServerSocket;
import java.lang.ClassNotFoundException;

public class Server {
    //socket server port on which it will listen
    private static int PORT = 8888;
    //static ServerSocket variable
    private static ServerSocket server;

    public static void main(String[] args) throws ClassNotFoundException {
        int i =0;
        try {
            server = new ServerSocket(PORT);
            while (true) {
                System.out.println("Waiting for the client request");

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
