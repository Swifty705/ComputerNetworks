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

import java.net.*;

class Client {
public static void main( String[] args) {
int port = 4444;
Socket socket;

/* parce args[0] to get ip address */

/* direct socket input to stdout, and direct stdin to socket output */
}//end main
}//end client class
