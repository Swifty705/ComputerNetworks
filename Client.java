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
import java.util.Scanner;
import java.util.regex.Pattern;

public class Client {
    public static void main(String[] args) throws IOException, InterruptedException {
        final String KEY = "OUR CHAT PROGRAM";
        final int portNumber = 4444; //statically set port number.
        Pattern ipPattern = Pattern.compile("^(?:(?:[0-9]{1,3}\\.){3}[0-9]{1,3}|localhost)$");
        String hostName;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter an IP address to connect to (enter \"localhost\" if you are hosting): ");
        hostName = connect(ipPattern, scanner);

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
                //encrypt output using AES-128
                out.println(new AES128().encrypt(KEY, inputLine));
                if (inputLine.equals("/quit")) {
                    System.out.println("Quitting...");
                    clientThread.interrupt();
                    socket.shutdownInput();
                    break;
                }
            }

        } catch (Exception e) {
            System.out.print("Oops! Something went wrong...\nError Message: " + e.getMessage()
                    + "\nShutting down now...");
        }
    }

    private static String connect(Pattern ipPattern, Scanner scanner){
        String hostName;
        while (!ipPattern.matcher(hostName = scanner.next()).find()) {
            System.out.println("Invalid IP. Please try again...");
            System.out.print("Enter an IP address to connect to (enter \"localhost\" if you are hosting): ");
        }
        return hostName;
    }
}