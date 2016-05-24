/* program: client.java
* Authors:  Theodore Cooke, Matt Swift, and Frank Liang
*
* description:
*  connects to a server on port 4444
* facilitates sending and recieving messages between other users on the server
* 
* invoke: java client <server_ip_address>
* port number is hardcoded
* 
* imports:
* java.net.* for Socket and clientSocket
* java.io.* for PrintWriter and BufferedReader (might not need
*/

import java.io.IOException;
import java.net.*;

class Client {
    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println(
                    "Usage: java EchoClient <host name>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = 4444;
        Socket socket = new Socket(hostName, portNumber);

        /* direct socket input to stdout, and direct stdin to socket output */
    }//end main
}//end client class
