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

public class ClientOutputThread extends Thread {
    Socket socket;

    public ClientOutputThread(Socket s) {
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
            System.out.println(
                    "+---------------------------------------------------------------------------------------+\n" +
                    "|     XXXXX XXXXX XXXXX X  X XXXXX XXXXX   XXXXX X   X XXXX XXXXX  X   X  XX     XXXX   |\n" +
                    "|     X     X   X X     X X  X       X     X     X   X X  X   X    X   X   X     X  X   |\n" +
                    "|     XXXXX X   X X     XX   XXXXX   X     X     XXXXX XXXX   X    X   X   X     X  X   |\n" +
                    "|         X X   X X     X X  X       X     X     X   X X  X   X     X X    X     X  X   |\n" +
                    "|     XXXXX XXXXX XXXXX X  X XXXXX   X     XXXXX X   X X  X   X      X    XXX  X XXXX   |\n" +
                    "+---------------------------------------------------------------------------------------+\n" +
                    "|Welcome to the Socket Rocket's Chat Program!                                           |\n" +
                    "|Partners on this project are: Ted Cooke, Frank Liang, and Matt Swift.                  |\n" +
                    "|If you'd like to broadcast a message simply type your message and hit [ENTER]          |\n" +
                    "|If you'd like to private message someone type /msg <username> <message> and hit [ENTER]|\n" +
                    "|If you'd like to list the current users type /who and hit [ENTER]                      |\n" +
                    "|If you'd like to quit type /quit and hit [ENTER]                                       |\n" +
                    "+---------------------------------------------------------------------------------------+");
            System.out.print("Please register your username: ");
            while ((outputLine = in.readLine()) != null) {
                System.out.print("\n" + new AES128().decrypt(KEY, outputLine));
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