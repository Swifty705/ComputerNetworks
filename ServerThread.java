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
            while (!(matcher = userName_pattern.matcher(in.readLine())).find()) {
                output.println("Sorry, usernames can only contain letters and numbers.");
                output.println("Please enter another username: ");
            }

            //Capture the username from the pattern group.
            username = matcher.group(1);

            //Welcome the newly registered user.
            this.output.format("Welcome, %s.\n", username);

            //Add the user to the HashMap of users.
            users.put(username, this.output);

            /*Show a list of currently connected users upon username registration,
            * by nature, this gets skipped over when the first user connects.*/
            connectedUsers(username);

            //Indicate to currently connected users when another user connects.
            if (users.entrySet().size() > 1) {
                for (Map.Entry<String, PrintWriter> entry : users.entrySet()) {
                    if (!entry.getKey().equals(username))
                        entry.getValue().println(username + " connected.");
                }
            }

            //Main input loop.
            while ((inputLine = in.readLine()) != null) {
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
                                entry.getValue().println(username + " disconnected."); //message everyone that the user d/c'ed.
                            break;
                        case "msg":  //if /msg
                            if ("" == message) //catch empty messages.
                                this.output.println("*Please supply a message to whisper to " + user);
                            else if (users.containsKey(user)) {
                                users.get(user).println(username + ": " + message); //send message.
                            }//end if user exists
                            else
                                this.output.println("*No such user " + user);
                            break;
                        default:
                            output.println("Sorry, the server did not recognize that command.");
                            break;
                    }
                } else { //else broadcast your message.
                    for (Map.Entry<String, PrintWriter> entry : users.entrySet()) {
                        if (!entry.getKey().equals(username))
                            entry.getValue().println(username + ": " + outputLine);
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
        }
    }

    private void connectedUsers(String currentUser) { //loop through hashmap and show connected users.
        if (users.entrySet().size() == 1) {
            if (users.containsKey(currentUser)){
                output.println("No other users are online.");
            }
        } else {
            output.println("Online users are:");
            for (Map.Entry<String, PrintWriter> entry : users.entrySet())
                if (!entry.getKey().equals(currentUser))
                    output.println(entry.getKey());
        }

    }
}

