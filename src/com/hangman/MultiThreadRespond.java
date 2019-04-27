package com.hangman;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadRespond implements Runnable{
    private ServerSocket server;
    private int port ;
    private ObjectOutputStream socketOutput;
    private ObjectInputStream socketInput;


    public MultiThreadRespond(int port){
        this.port = port;
        try{
            server = new ServerSocket(port);
        }catch(Exception e){

        }
    }

    @Override
    public void run() {
        while(true){
            try{
                Socket socket = server.accept();
                //read from socket to ObjectInputStream object
                socketInput = new ObjectInputStream(socket.getInputStream());
                //convert ObjectInputStream object to String
                String message = (String) ois.readObject();
                System.out.println("Message Received: " + message);
                //create ObjectOutputStream object
                socketOutput = new ObjectOutputStream(socket.getOutputStream());
                //write object to Socket
                socketOutput.writeObject("Hi Client "+message);
                //close resources
                socketInput.close();
                socketOutput.close();
                socket.close();
                //terminate the server if client sends exit request
                if(message.equalsIgnoreCase("exit")) break;


            }catch(Exception e){

            }

        }

    }
}
