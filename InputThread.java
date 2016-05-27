/* class: InputThread.java
* Authors:  Theodore Cooke, Matt Swift, and Frank Liang
*
* description:
* creates a stream from standard input to a socket output
* 
* imports:
* java.net.* for Socket and clientSocket
* java.io.* for PrintWriter and BufferedReader 
*/


import java.io.*;
import java.net.*;


public class InputThread implements Runnable
{Socket socket;

public InputThread(Socket s)
{this.socket = s;}


public void run() 
{
//}catch (IOException e)
//{System.err.println("Error: failed to get input stream from the server");
}catch InterruptedException e)
{System.err.println("input thread was interrupted");
finally
{System.exit(1);}
}//end inputDaemon
}//end class
