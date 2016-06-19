/*
    Authors: Theodore Cooke, Matt Swift, Frank Liang.
    Description: Creates a Server on port 4444
    Routes messages between different clients.

    invoke: java Server
    no port number needed (hardcoded).
 */

import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.regex.*;

public class Server {
    private static HashMap<String, PrintWriter> users = new HashMap<>();

    public static void main(String[] args) throws IOException {
        int portNumber = 4444; //statically set port number.

        //define regex patterns.
        ServerIOThread.userName_pattern = Pattern.compile("^(\\w+)\\s*");
        ServerIOThread.input_pattern = Pattern.compile("^/(\\w+) *(\\w*) *(.*)");
		
		System.out.println("Server started.\nNow listening for connections...");

        //try listening for connections.
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (true) {
                new ServerIOThread(serverSocket.accept(), users).start();
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }

}
