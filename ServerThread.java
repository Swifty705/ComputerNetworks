import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by Matthew on 2016-05-25.
 */
public class ServerThread extends Thread {
    //Regex pattern to search for strings with a forward slash.
    private Pattern pattern = Pattern.compile("/^/[a-z,0-9]+$/i");
    //Socket to be used for connection, passed in constructor.
    private Socket socket = null;
    //Reference to a Hash Map created in the Server class, passed in constructor.
    private Map<String, Socket> users = null;

    //Constructor for Server Thread.
    public ServerThread(Socket socket, Map<String, Socket> users){
        super("ServerThread");
        this.socket = socket;
        this.users = users;
    }

    //Mandatory method inherited from Thread
    public void run(){
        try(
                //Print Writer to write to client. Grabs the Output Stream on the socket.
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                //Input Stream Reader wrapped in a Buffered Reader, grabs input stream of socket.
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            //Declaring an input string, output string, and user name.
            String inputLine, outputLine, userName;
            //Output a welcome message.
            out.println("Welcome to the Socket Rocket's Chat v1.\nIf you wish to send a Private Message type \'/\'<username>.\n" +
                    "otherwise your message is broadcast. If you wish to see a list of connected users type \'/\'who.");
            //Output a command to register a username.
            out.println("First, please register a username: ");

            //This next block sets up your username.
            if(in.readLine().equals("who")){
                out.println("That username is unavailable, please choose another.");
            } else {
                userName = in.readLine();
                add(userName, socket);
            }

            //This next block is intended for sending a message.
            //Some testing is thrown in as it's yet incomplete.
            while((inputLine = in.readLine()) != null){

                if(pattern.matcher(inputLine).find()){
                    outputLine = "Match found!";
                    out.println(outputLine);
                    int i = inputLine.indexOf(" ");
                    String username = inputLine.substring(1, i);
                    System.out.println(i);
                    System.out.println(username);
                }
                //outputLine = inputLine;
            }
            //Close the connection.
            socket.close();
            //Catch any exceptions.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //send a private message. TO DO.
    private void privateMessage(String senderUsername, Socket receiver, String msg){

    }

    //send a broadcast. TO DO.
    private void broadcast(Socket sender, String msg){

    }

    //Add a user to the hash table.
    private void add(String username, Socket s){
        if(!users.containsKey(username))
            users.put(username, s);
    }

    //Show a list of users. TO DO.
    private void showUsers(){

    }
}
