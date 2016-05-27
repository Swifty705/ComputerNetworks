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
        //Hash Map to be passed into a server thread.
        Map<String, Socket> users = new HashMap<>();

        //port number for connections to the server.
        int portNumber = 4444;

        //attempt to listen and accept connections on the port, creating a new socket.
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (true) {
                new ServerThread(serverSocket.accept(), users).start();
            }
        } catch (IOException e) { //catch any connection exceptions.
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }
}