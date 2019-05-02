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

    private int MAX_TRY = 7;

    public MultiThreadRespond(int port){
        this.port = port;
        try{
            server = new ServerSocket(port);
        }catch(Exception e){

        }
    }

    @Override
    public void run() {
        //Determine Word
        String  [] Word = { "Harrypotter","Friendzone","Spiderman","Alita",
                "Frozen","Batman","Deadpool","Sinderella", "Avatar",
                "Titanic","Avengers","Incredibles","Minions", "Aquaman ",
                "Transformers","Skyfall","Zootopia", "Jumanji"};
        String rand_word;
        char[] hidden_word;
        String user_guess = "";
        int miss_chance = 0;
        char[] missed = new char[7];

        //Random word to Client
        rand_word = Word[ (int)(Math.random() * Word.length) ].toLowerCase();
        //Determine all hidden_word equals Word that random
        hidden_word = new char[rand_word.length()];

        int isWin = 0;
        int isLose = 0;

        for (int i = 0; i < rand_word.length(); i++) {
            hidden_word[i] = '_';
        }

        StringBuilder res_hidden_word = new StringBuilder();
        int  res_miss_count = miss_chance;
        StringBuilder res_missed = new StringBuilder();

        ////   hidden_word|miss_count|missed|isWin|isLose

        System.out.println("Start Games : " + rand_word);
        while(true){
            try{
                Socket socket = server.accept();
                socketInput = new ObjectInputStream(socket.getInputStream());
                socketOutput = new ObjectOutputStream(socket.getOutputStream());
                boolean running = true;
                while (running){
                    //Hidden word
                    System.out.print("Hidden Word: ");
                    res_hidden_word.delete(0,  res_hidden_word.length());
                    for (int i = 0; i < rand_word.length(); i++) {
                        System.out.print(hidden_word[i] + " ");
                        res_hidden_word.append(hidden_word[i]).append(" ");
                    }

                    //Misses word
                    System.out.print("\nMisses: ");
                    res_missed.delete(0,  res_missed.length());
                    for (int i = 0; i < missed.length; i++) {
                        System.out.print(missed[i]);
                        res_missed.append(missed[i]).append(" ");
                    }

                    // Check action which Clint do
                    String action =  (String) socketInput.readObject();
                    System.out.println(action);
                    if (action.equals("start") || action.equals("getStatus")){
                        //Response games status
                        res_miss_count = miss_chance;
                        //Separator with @ --> split response with @
                        socketOutput.writeObject(res_hidden_word + "@" + res_miss_count + "@" + res_missed + "@" + isWin + "@" + isLose );
                    }else if (action.equals("getAnswer")) {
                        //Show answer that random to Client
                        socketOutput.writeObject(rand_word);
                    }else if (action.equals("exit")) {
                        //Close Socket
                        socketOutput.close();
                        socketInput.close();
                        socket.close();
                    }else if (action.substring(0,5).equals("send:")){
                        // Get Client input
                        user_guess = action.substring(5,6);
                        System.out.print("Guess: " + user_guess+"\n");

                        // Game Logical
                        int hidden_count=rand_word.length();
                        //count miss
                        if (!rand_word.contains(user_guess)){
                            miss_chance++;
                        }

                        //Count the remaining words
                        if (rand_word.contains(user_guess)) {
                            hidden_count--;
                            if (hidden_count == 0) {
                                isWin++;
                                break;
                            }
                        }
                        //count miss word
                        if (miss_chance>=MAX_TRY){
                            isLose++;
                            break;
                        }
                    }

                }
            }catch(Exception e){
                System.out.println(e);
            }

        }
    }
}
