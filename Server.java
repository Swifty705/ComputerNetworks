/* program: server.java
* Authors:  Theodore Cooke, Matt Swift, and Frank Liang
*
* description:
*  bootstraps the server
* accepts client sockets on port 4444
* performs handshakes with users
* passes validated client sockets to the router
* 
* invoke: java Server
* port number is hardcoded
* 
* imports:
* java.net.* for Socket and ServerSocket
* java.io.* for PrintWriter and BufferedReader (might not need
*/

import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Server {
    public static void main(String[] args) throws IOException {
        Map<String, Socket> users = new HashMap<>();

        if (args.length != 1) {
            System.err.println("Usage: java Server");
            System.exit(1);
        }

        int portNumber = 4444;

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (true) {
                new ServerThread(serverSocket.accept(), users).start();
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }
}