/* class: OutputThread.java
* Authors:  Theodore Cooke, Matt Swift, and Frank Liang
*
* description:
* creates a stream from a socket input to standard output
* 
* imports:
* java.net.* for Socket and clientSocket
* java.io.* for PrintWriter and BufferedReader 
*/


import java.io.*;
import java.net.*;


public class OutputThread extends Thread
{Socket socket;

public OutputThread(Socket s)
{this.socket = s;}


public void run() 
{String outputLine;

try (
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()))
){
            while ((outputLine = in.readLine()) != null ) {
System.err.println("read a line");
                if (outputLine.equals("Bye."))
{                System.out.println("*Server closed the connection");
break;
}
else
                System.out.println(outputLine);
}//end while
}catch (IOException e)
{if (socket.isConnected())
System.err.println("socket was just closed");
else
System.err.println("Error: failed to get output stream from the server.");}

finally
{System.exit(1);}
}//end outputDaemon
}//end class
