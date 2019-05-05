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

    public String getHints(String rand_word,String Word[]){
        String Hints;

        if(rand_word.equals(Word[0].toLowerCase()))
            Hints = "Wizzards Have magic wand and spell Wingardium Leviosa  ";
        else if(rand_word.equals(Word[1].toLowerCase()))
            Hints = "morethan friend but lessthan girlfriend";
        else if(rand_word.equals(Word[2].toLowerCase()))
            Hints = "Red clothes can climb the tower";
        else if(rand_word.equals(Word[3].toLowerCase()))
            Hints = "She is a cybrog";
        else if(rand_word.equals(Word[4].toLowerCase()))
            Hints = "Disney Princess with snow world";
        else if(rand_word.equals(Word[5].toLowerCase()))
            Hints = "Dark Knight Hero of the Gotham City";
        else if(rand_word.equals(Word[6].toLowerCase()))
            Hints = "Red Clothes funny and joke";
        else if(rand_word.equals(Word[7].toLowerCase()))
            Hints = "Disney Princess who has lost her shoes for the man :X";
        else if(rand_word.equals(Word[8].toLowerCase()))
            Hints = "Blue skin in the beautiful word speak navi language";
        else if(rand_word.equals(Word[9].toLowerCase()))
            Hints = "Romantic movie in the ship but bad ending in the end";
        else if(rand_word.equals(Word[10].toLowerCase()))
            Hints = "Mix Marvel Heroes";
        else if(rand_word.equals(Word[11].toLowerCase()))
            Hints = "HEROES FAMILY";
        else if(rand_word.equals(Word[12].toLowerCase()))
            Hints = "Yellow tiny body gang with funny moment";
        else if(rand_word.equals(Word[13].toLowerCase()))
            Hints = "Atlantis DC HEROES";
        else if(rand_word.equals(Word[14].toLowerCase()))
            Hints = "Epic Robot can transform their body";
        else if(rand_word.equals(Word[15].toLowerCase()))
            Hints = "007";
        else if(rand_word.equals(Word[16].toLowerCase()))
            Hints = "Cartoon full of animals";
        else
            Hints = "4 guys from reality to the game";

        return Hints;
    }

    @Override
    public void run() {
        //Determine Word
        String  [] Word = { "Harrypotter","Friendzone","Spiderman","Alita",
                "Frozen","Batman","Deadpool","Sinderella", "Avatar",
                "Titanic","Avengers","Incredibles","Minions", "Aquaman",
                "Transformers","Skyfall","Zootopia", "Jumanji"};
        String rand_word;
        char[] hidden_word;
        String user_guess = "";
        int miss_chance = 0;
        char[] missed = new char[7];
        boolean letter_found = false;
        boolean solved = false;
        String Hints;

        //Random word to Client
        rand_word = Word[ (int)(Math.random() * Word.length) ].toLowerCase();
        Hints = getHints(rand_word,Word);
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

        // hidden_word คือช่องว่างที่ให้เติม|miss_count นับตัวที่เดาผิด|missed ตัวที่ผิด |isWin บอกว่าขนะ |isLose บอกว่าาแพ้

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
                    }else if (action.equals("getHints")) {
                        //Show answer that random to Client
                        socketOutput.writeObject(Hints);
                    } else if (action.equals("exit")) {
                        //Close Socket
                        socketOutput.close();
                        socketInput.close();
                        socket.close();
                    }else if (action.substring(0,5).equals("send:")){
                        // Get Client input
                        user_guess = action.substring(5,6);
                        System.out.print("Guess: " + user_guess+"\n");

                        // Game Logical
                        letter_found = false;
                        for (int i = 0; i < rand_word.length(); i++) {
                            if (user_guess.toLowerCase().charAt(0) == rand_word.toLowerCase().charAt(i)) {
                                hidden_word[i] = rand_word.charAt(i);
                                letter_found = true;
                            }
                        }
                        if (!letter_found) {
                            missed[miss_chance] = user_guess.charAt(0);
                            miss_chance++;
                        }
                        int hidden_count = 0;
                        for (int i = 0; i < rand_word.length(); i++) {
                            if ('_' == hidden_word[i])
                                hidden_count++;
                        }
                        if (hidden_count > 0) {
                            solved = false;
                        } else{
                            solved = true;
                        }
                    }

                    /// check win or lose
                    if (miss_chance >= MAX_TRY){
                        isLose = 1;
                    }
                    if (solved){
                        isWin = 1;
                    }
                    System.out.println("---------------------------------------------------------------");
                }
            }catch(Exception e){
                System.out.println(e);
            }

        }
    }
}
