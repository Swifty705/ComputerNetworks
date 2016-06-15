/*
    Authors: Theodore Cooke, Matt Swift, and Frank Liang
    Description: Thread extension that handles routing messages
    between all or two users. Allows for showing all connected users, and graceful shutdown.
    To be used in conjunction with Server.java
 */

import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.*;

public class ServerThread extends Thread {
    public static Pattern userName_pattern;
    public static Pattern input_pattern;
    private Matcher matcher;
    private static HashMap<String, PrintWriter> users = null;
    private Socket socket = null;
    private PrintWriter output;
    private static final String KEY = "OUR CHAT PROGRAM";

    //Constructor, parameters: Socket, HashMap, holds an instance reference to each.
    public ServerThread(Socket socket, HashMap<String, PrintWriter> userEntries) throws IOException {
        super("ServerThread");
        this.socket = socket;
        this.output = new PrintWriter(socket.getOutputStream(), true);
        users = userEntries;
    }

    public void run() {

        try (
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()))
        ) {
            //Declare input, output, and username strings.
            String inputLine, outputLine, username;

            //Loop until a proper username is entered.
            while (!(matcher = userName_pattern.matcher(new AES128().decrypt(KEY, in.readLine()))).find()) {
                output.println(new AES128().encrypt(KEY, "Sorry, usernames can only contain letters and numbers."));
                output.println(new AES128().encrypt(KEY, "Please enter another username: "));
            }

            //Capture the username from the pattern group.
            username = matcher.group(1);

            //Welcome the newly registered user.
            this.output.println(new AES128().encrypt(KEY, String.format("Welcome, %s.", username)));

            //Add the user to the HashMap of users.
            users.put(username, this.output);

            /*Show a list of currently connected users upon username registration,
            * by nature, this gets skipped over when the first user connects.*/
            connectedUsers(username);

            //Indicate to currently connected users when another user connects.
            if (users.entrySet().size() > 1) {
                for (Map.Entry<String, PrintWriter> entry : users.entrySet()) {
                    if (!entry.getKey().equals(username))
                        entry.getValue().println(new AES128().encrypt(KEY, username + " connected."));
                }
            }

            //Main input loop.
            while ((inputLine = new AES128().decrypt(KEY, in.readLine())) != null) {
                //Create pattern matcher from input.
                matcher = input_pattern.matcher(inputLine);
                //Set the output to become the input.
                outputLine = inputLine;
                if (matcher.find()) { //the line starts with a slash
                    String cmd = matcher.group(1); //for commands like /msg, /who, /quit
                    String user = matcher.group(2); //to capture the username.
                    String message = matcher.group(3); //to capture the message.
                    System.out.format("%s commands %s to %s with %s\n", username, cmd, user, message);
                    switch (cmd) {
                        case "who":  //if /who
                            connectedUsers(username); //list users.

                            break;
                        case "quit":  //if /quit
                            users.remove(username); //remove this user.

                            for (Map.Entry<String, PrintWriter> entry : users.entrySet())
                                entry.getValue().println(new AES128().encrypt(KEY, username + " disconnected.")); //message everyone that the user d/c'ed.
                            break;
                        case "msg":  //if /msg
                            if ("" == message) //catch empty messages.
                                this.output.println(new AES128().encrypt(KEY, "*Please supply a message to whisper to " + user));
                            else if (users.containsKey(user)) {
                                users.get(user).println(new AES128().encrypt(KEY, username + ": " + message)); //send message.
                            }//end if user exists
                            else
                                this.output.println(new AES128().encrypt(KEY, "*No such user " + user));
                            break;
                        default:
                            output.println(new AES128().encrypt(KEY, "Sorry, the server did not recognize that command."));
                            break;
                    }
                } else { //else broadcast your message.
                    for (Map.Entry<String, PrintWriter> entry : users.entrySet()) {
                        if (!entry.getKey().equals(username))
                            entry.getValue().println(new AES128().encrypt(KEY, username + ": " + outputLine));
                    }
                } //end entire if-else block.
            }
            socket.close(); //close connection if user is no longer connected.
        } catch (SocketException e) {
            System.out.println("Error! Did a client disconnect unexpectedly? " + e.getMessage() + ".");
        } catch (IOException e) { //catch exceptions.
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("Error! " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void connectedUsers(String currentUser) throws Exception { //loop through hashmap and show connected users.
        if (users.entrySet().size() == 1) {
            if (users.containsKey(currentUser)){
                output.println(new AES128().encrypt(KEY, "No other users are online."));
            }
        } else {
            output.println(new AES128().encrypt(KEY, "Online users are:"));
            for (Map.Entry<String, PrintWriter> entry : users.entrySet())
                if (!entry.getKey().equals(currentUser))
                    output.println(new AES128().encrypt(KEY, entry.getKey()));
        }

    }
}

