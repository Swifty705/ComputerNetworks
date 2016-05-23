/* program: server.java
* Authors:  Theodore Cooke, Matt Swift, and Frank Liang
*
* description:
*  bootstraps the server
* accepts client sockets on port 4444
* performs handshakes with users
* passes validated client sockets to the router
* 
* invoke: java server
* port number is hardcoded
* 
* imports:
* java.net.* for Socket and ServerSocket
* java.io.* for PrintWriter and BufferedReader (might not need
*/

import java.io.*;
import java.net.*;

public class Server {
static Router router;

public static void main( String[] args)  throws IOException {
int port = 4444;
router = new Router();

/* create server socket */
ServerSocket serverSocket = new ServerSocket( 4444);
//error handling in case socket isn't created

/*continually accept new sockets, passing them to the handshake function */
while ( true )
	handshake( serverSocket.accept());
}//end main


/* handshake function
* establishes the client username
* adds the client to the router
* valid usernames follow same pattern as valid variables
* params: Socket */
private static void handshake( Socket s) {
String username = "";

/* write to socket, asking for username */
/* validate username */
/* add username to router */
router.add( username, s);
}//end handshake function
}//end Server class
