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


import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException {
        
        if (args.length != 2) {
            System.err.println(
                "Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = "0.0.0.0"; //args[0];
        int portNumber = 4444; //Integer.parseInt(args[1]);
Thread t;

        try (
            Socket socket = new Socket(hostName, portNumber);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()))
        ) {

            String fromServer;
            String fromUser;
t  = new  Thread(new InputThread( socket));
t.start();

            while ((fromServer = in.readLine()) != null) {
                System.out.println("Server: " + fromServer);
                if (fromServer.equals("*Bye"))
{                System.out.println("*Server closed the connection");
                    break;
}
                
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        }
finally 
{t.close();}
    }
}