import javax.swing.plaf.synth.SynthTextAreaUI;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
public class Client {

    private static int MAX_TRY = 7;
    private static int INIT_PORT = 8888;
    private static String HOST = "127.0.0.1";

    private static int miss_chance = 0;
    private static Socket socket = null;
    private static ObjectOutputStream socketOutput = null;
    private static ObjectInputStream socketInput = null;

    private static String hidden_word = "";
    private static String missed = "";

    private static int isWin = 0;
    private static int isLose = 0;


    private static void getStatus(){
        try {
            socketOutput.writeObject("getStatus");
            String input = (String) socketInput.readObject();
            String[] res = input.split("@");  // split response with @ and store in String arrays

            // assign each status to global variable
            hidden_word = res[0];
            miss_chance = Integer.parseInt(res[1]);
            missed = res[2];
            isWin = Integer.parseInt(res[3]);
            isLose = Integer.parseInt(res[4]);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private static void drawman(){


         if(miss_chance == 1) {
             System.out.println("         ____________");
             System.out.println("        |      |     |");
             System.out.println("        |            |");
             System.out.println("        |            |");
             System.out.println("        |            |");
             System.out.println("        |            |");
             System.out.println("         ____________");
             System.out.print("\n");
        }

        else if(miss_chance == 2) {
            System.out.println("         ____________");
            System.out.println("        |      |     |");
            System.out.println("        |      0     |");
            System.out.println("        |            |");
            System.out.println("        |            |");
            System.out.println("        |            |");
            System.out.println("         ____________");
            System.out.print("\n");
        }

         else if(miss_chance == 3) {
             System.out.println("         ____________");
             System.out.println("        |      |     |");
             System.out.println("        |      0     |");
             System.out.println("        |      |     |");
             System.out.println("        |      |     |");
             System.out.println("        |            |");
             System.out.println("         ____________");
             System.out.print("\n");
         }

         else if(miss_chance == 4) {
             System.out.println("         ____________");
             System.out.println("        |      |     |");
             System.out.println("        |      0     |");
             System.out.println("        |      |     |");
             System.out.println("        |      |     |");
             System.out.println("        |     /      |");
             System.out.println("         ____________");
             System.out.print("\n");
         }

         else if(miss_chance == 5) {
             System.out.println("         ____________");
             System.out.println("        |      |     |");
             System.out.println("        |      0     |");
             System.out.println("        |    /_|     |");
             System.out.println("        |      |     |");
             System.out.println("        |     /      |");
             System.out.println("         ____________");
             System.out.print("\n");
         }

         else if(miss_chance == 6) {
             System.out.println("         ____________");
             System.out.println("        |      |     |");
             System.out.println("        |     0|      |");
             System.out.println("        |     /|     |");
             System.out.println("        |      |     |");
             System.out.println("        |      /     |");
             System.out.println("         ____________");
             System.out.print("\n");
         }

         else if(miss_chance == 7) {
             System.out.println(" ...........           .         ....            ....   ...........    ............   .           .   ...........   ............  ");
             System.out.println(" .                   .   .       .   .          .   .   .              .          .    .         .    .             .            .");
             System.out.println(" .                  .     .      .    .        .    .   .              .          .     .       .     .             .            .");
             System.out.println(" .    ......       . . . . .     .     .      .     .   ............   .          .      .     .      ...........   ............. ");
             System.out.println(" .         .      .         .    .      .    .      .   .              .          .       .   .       .             .          .  ");
             System.out.println(" .         .     .           .   .       .  .       .   .              .          .        . .        .             .           . ");
             System.out.println(" ...........    .             .  .        ..        .   ............   ............         .         ...........   .             . ");

         }


    }//drawman

    private static String getAnswer(){
        try {
            socketOutput.writeObject("getAnswer");
            String input = (String) socketInput.readObject();
            return input;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String getHints(){
        try {
            socketOutput.writeObject("getHints");
            String input = (String) socketInput.readObject();
            return input;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static void sendUserInput(String s){
        try {
            socketOutput.writeObject("send:"+s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main( String[] args ) {
        Scanner keyboard;

        System.out.println("Connect to server " + HOST + ":" + INIT_PORT);
        try {
            // init socket with init port
            socket = new Socket(HOST, INIT_PORT);
            // send "start" to get new port
            socketOutput = new ObjectOutputStream(socket.getOutputStream());
            socketOutput.writeObject("start");

            // get new port to change connection
            socketInput = new ObjectInputStream(socket.getInputStream());
            String newPort = (String) socketInput.readObject();
            System.out.println("new Port: " + newPort);
            keyboard = new Scanner(System.in);

            //// switch to new port
            socket = new Socket(HOST, Integer.parseInt(newPort));
            socketOutput = new ObjectOutputStream(socket.getOutputStream());
            socketInput = new ObjectInputStream(socket.getInputStream());

            // get initial game status
            getStatus();

            while (isWin == 0 && isLose ==0) {


                // print game status
                //System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-\n");
                System.out.println("You have " + (MAX_TRY - miss_chance) + " turns left.");
                System.out.println("Word:\t" + hidden_word);
                System.out.println("Misses: " + missed);
                System.out.println("Hints: "+getHints());
                System.out.print("\nGuess: ");


                // send user input
                sendUserInput(keyboard.nextLine());

                // get new game status
                getStatus();
                drawman();




            } //While loop

            //System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");

            if (isWin==1){
                System.out.println( "Yeah! you Win" );
                System.out.println( "The word was..." + hidden_word );

            }
            else if (isLose ==1) {
                String answer = getAnswer();
                System.out.println("Noooooob guy!!");
                System.out.println("The word was..." + answer);
            }

            // send "exit" for tell server thead to close socket
            socketOutput.writeObject("exit");

            // close socket
            socketOutput.close();
            socketInput.close();
            socket.close();

        }
        catch (IOException e) {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}

