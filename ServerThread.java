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
    private Pattern pattern = Pattern.compile("/^/[a-z0-9]+$/i");
    private Socket socket = null;
    private Map<String, Socket> users = null;

    public ServerThread(Socket socket, Map<String, Socket> users){
        super("ServerThread");
        this.socket = socket;
        this.users = users;
    }

    public void run(){
        try(
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            String inputLine, outputLine, userName;
            out.println("Welcome to the Socket Rocket's Chat v1.\nIf you wish to send a Private Message type \'/\'<username>.\n" +
                    "otherwise your message is broadcast. If you wish to see a list of connected users type \'/\'who.");
            out.println("First, please enter a username: ");

            if("who".equals(in.readLine())){
                out.println("That username is unavailable, please choose another.");
            } else {
                userName = in.readLine();
                add(userName, socket);
            }

            while((inputLine = in.readLine()) != null){
                if(pattern.matcher(inputLine).find()){
                    outputLine = "Match found!";
                    out.println(outputLine);
                }
                //outputLine = inputLine;
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void privateMessage(Socket sender, Socket receiver, String msg){

    }

    private void broadcast(Socket sender, String msg){

    }

    private void add(String username, Socket s){
        if(!users.containsKey(username))
            users.put(username, s);
    }

    private void showUsers(){

    }
}
