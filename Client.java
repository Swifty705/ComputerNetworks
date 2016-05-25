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
    public static void main(String[] args) throws IOException {
final int portNumber = 4444;
String hostName = "0.0.0.0";
        
        if (args.length == 0) {
            System.out.println("Using default host");
        }
else
        hostName = args[0];

        try (
            Socket socket = new Socket(hostName, portNumber);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()))
        ) {
(new  Thread(new InputThread( socket))).start();

            String fromServer;

//try {
            while ((fromServer = in.readLine()) != null) {
                if (fromServer.equals("*Bye"))
{                System.out.println("*Server closed the connection");
break;
}
else
                System.out.println(fromServer);
                
            }
/*}catch (UnknownHostException e)
{System.err.println("Error: Stream from server was closed");
//System.exit(1);
throw e;
}*/
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        }
finally
{System.exit(1);}
    }
}