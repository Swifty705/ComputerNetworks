/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ServerThread extends Thread {
    private Socket socket = null;
    private HashMap<String, Socket> users;

    public ServerThread(Socket socket, HashMap<String, Socket> users) {
        super("ServerThread");
        this.socket = socket;
        this.users = users;
    }

    public void run() {

        try (
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()))
        ) {
            String inputLine, outputLine, username;

            //This block is intended for welcoming the user and registering a username.
            out.println("Welcome to the Socket Rocket's Chat Server v1. If you would like to send a " +
                    "private message type /<username>, otherwise your message is broadcast. If you would " +
                    "like to see a list of current users type /who. Please register a username: ");
            if((username = in.readLine()) != null)
                if(username.equals("who") || username.equals("quit"))
                    throw new IOException("Sorry, that is an invalid username.");
                users.put(username, socket);

            //This block is intended for sending your message.
            while ((inputLine = in.readLine()) != null) {
                if(inputLine.startsWith("/")){
                    String receiver = inputLine.substring(1, inputLine.indexOf(" "));
                    if(users.containsKey(receiver))
                        privateMessage(username, users.get(receiver), inputLine.substring(inputLine.indexOf(" "), inputLine.length()+1));
                } else {
                    outputLine = inputLine;
                    out.println(outputLine);
                }

                if (inputLine.equals("/quit"))
                    break;
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void privateMessage(String sender, Socket receiver, String msg){
        String privateOutput;
        try {
            PrintWriter out = new PrintWriter(receiver.getOutputStream(), true);
            privateOutput = sender + ":" + msg;
            out.println(privateOutput);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

