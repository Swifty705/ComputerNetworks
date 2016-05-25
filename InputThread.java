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
{String inputLine;

try (
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader stdIn =
                new BufferedReader(new InputStreamReader(System.in));
){
while(  (inputLine = stdIn.readLine()) != null)
{System.err.println("sending "+inputLine);
	out.println(inputLine);
//g	out.println(line);
}
}catch (IOException e)
{System.err.println("Error: failed to connect to the server");
System.err.println("Error: failed to connect to the server");
}finally
{System.exit(1);}
}//end inputDaemon
}//end class
