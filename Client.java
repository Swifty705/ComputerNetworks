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
        final int portNumber = 4444; //statically set port number.
        String hostName = "0.0.0.0"; //default host.

        if (args.length == 0) {
            System.out.println("Using default host. If you wish to specify a host provide a hostname as an argument.");
        } else
            hostName = args[0]; //if provided as argument, set the arg as hostname.

        try ( //try creating a new socket connection.
                Socket socket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                BufferedReader stdIn =
                        new BufferedReader(new InputStreamReader(System.in))
        ) {
            //pipe and process output through an output thread client-side.
            ClientThread clientThread = new ClientThread(socket);

            clientThread.start();
            String inputLine;

            //listen for input on System.in
            while ((inputLine = stdIn.readLine()) != null) {
                out.println(inputLine);
                if (inputLine.equals("/quit")) {
                    System.out.println("Quitting...");
                    clientThread.interrupt();
                    socket.shutdownInput();
                    break;
                }
            }

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't establish socket to " +
                    hostName);
            System.exit(1);
        }
    }
}