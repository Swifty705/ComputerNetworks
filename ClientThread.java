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

public class ClientThread extends Thread {
    Socket socket;

    public ClientThread(Socket s) {
        this.socket = s;
    }
    private static final String KEY = "OUR CHAT PROGRAM";


    public void run() {
        String outputLine;

        try (
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()))
        ) {
            //Present a welcome message on connection.
            System.out.println("Welcome to the Socket Rocket's Chat Program! \n" +
                    "Partners on this project are: Ted Cooke, Frank Liang, and Matt Swift.\n" +
                    "If you would like to private message someone type /msg <username> <message>.\n" +
                    "If you would like to list the current users type /who.\n" +
                    "If you would like to quit type /quit.");
            System.out.print("Please register a username: ");
            while ((outputLine = in.readLine()) != null) {
                System.out.println(new AES128().decrypt(KEY, outputLine));
            }//end while
        } catch (IOException e) {
            if (!socket.isConnected()) {
                System.err.println("Error: failed to get output stream from the server.");
                System.exit(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//end outputDaemon
}//end class
