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

	public static void main(String[] args) throws IOException {
		int port = 4444;

	/* create server socket */
		ServerSocket serverSocket = new ServerSocket(4444);
	//error handling in case socket isn't created

	/*continually accept new sockets, passing them to the handshake function */
		while (true)
			handshake(serverSocket.accept());
	}//end main


	/* handshake function
    * establishes the client username
    * adds the client to the router
    * valid usernames follow same pattern as valid variables
    * params: Socket */
	private static void handshake(Socket s) {
		String username = "";

	/* write to socket, asking for username */
	/* validate username */
	/* add username to router */
		add(username, s);
	}//end handshake function

	private static void add( String username, Socket s) {
	String input;

	/* write to every user *username has joined. */
	/* add username and socket in the hash */
	/* maybe somewhere in here, wrap buffers around the input/output streams for faster processing */

	/* while the socket still exists, accept input and rout it to the correct function */
	while ( null != s) {
	//input = s.readline();
	// Ted advises using regex. good tutorial found here http://www.tutorialspoint.com/java/java_regular_expressions.htm
	//regex is good to learn sooner rather than later
	//if input not starts with /, send to all
	//this.sendToAll( s, "test send"); // format : username: input
	//else{
	//parse first word
	//if else statements to rout to correct function
	//}//end if input was command
	}//end wile socket exists

	/* delete socket */
	//this.sendToAll( s, "test delete"); //format: *username has left.
	}//end add

	private static void sendToAll( Socket sender, String msg) {
	/* iterate through all sockets except for sending socket, sending input String */
	}//end sendToAll

	private static void sendToOne( Socket sender, Socket reciever, String msg) {
	/* stuff  */
	}//end sendToOne



	/*the remaining functions are invoked when the user starts a msg with '/' */


	private static void nick(Socket s, String nickname) {
	/* validate new nickname */
	/* send user error msg if it is invalid */
	/* else send all other users message indicating the update, and update the hash */
	}//end nick

	private static void quit() {
	//delete socket, and maybe some other smart cleanup actions */
	}//end quit

}//end Server class
