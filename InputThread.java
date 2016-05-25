import java.io.*;
import java.net.*;
//import java.lang.*;


public class InputThread implements Runnable
{Socket socket;

public InputThread(Socket s)
{this.socket = s;}


public void run() 
{String line;
Socket s = socket;
try
(
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
){
            BufferedReader stdIn =
                new BufferedReader(new InputStreamReader(System.in));

while(  (line = stdIn.readLine()) != null)
{System.err.println("sending "+line);
	out.println(line);
	out.println(line);
}
}catch (IOException e)
{System.err.println("Error: failed to establish stream to the server");
}finally
{System.exit(1);}
}//end inputDaemon
}//end class
