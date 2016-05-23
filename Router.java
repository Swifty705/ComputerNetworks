/* object: router.java
* Authors:  Theodore Cooke, Matt Swift, and Frank Liang
*
* description:
*  routs messages between client sockets
* 
* imports:
* java.net.* for Socket and routerSocket
* java.io.* for PrintWriter and BufferedReader (might not need
* probably a regexp library
*/

import java.net.*;
import java.io.*;

class Router {
//private hashtable/associative array users; (keys are usernames and values are their sockets)

/* constructor
* initialises the socket array */
public Router() {
//users = new hashtable or associativie array or something
}//end constructor

public void add( String username, Socket s) {
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

private void sendToAll( Socket sender, String msg) {
/* iterate through all sockets except for sending socket, sending input String */
}//end sendToAll

private void sendToOne( Socket sender, Socket reciever, String msg) {
/* stuff  */
}//end sendToOne



/*the remaining functions are invoked when the user starts a msg with '/' */


private void nick(Socket s, String nickname) {
/* validate new nickname */
/* send user error msg if it is invalid */
/* else send all other users message indicating the update, and update the hash */
}//end nick

private void quit() {
//delete socket, and maybe some other smart cleanup actions */
}//end quit
}//end router class
