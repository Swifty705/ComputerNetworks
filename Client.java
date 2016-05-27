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
* java.io.* for PrintWriter and BufferedReader 
*/


import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException, InterruptedException {
final int portNumber = 4444;
String hostName = "0.0.0.0";
        
        if (args.length == 0) {
            System.out.println("Using default host");
        }
else
        hostName = args[0];

        try (
            Socket socket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader stdIn =
                new BufferedReader(new InputStreamReader(System.in));
        ) {
OutputThread outputThread = new  OutputThread( socket);

outputThread.start();
String inputLine;

while(  (inputLine = stdIn.readLine()) != null)
{System.err.println("sending |"+inputLine+"|");
if ( inputLine.equals("/quit"))
{System.out.println("quitting");
outputThread.interrupt();
socket.shutdownInput();
break;
}
else
	out.println(inputLine);
}

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't establish socket to " +
                hostName);
            System.exit(1);
        }
System.out.println("closed");
return;
    }
}