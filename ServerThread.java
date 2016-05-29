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
import java.util.regex.*;

public class ServerThread extends Thread {
public static Pattern userName_pattern;
public static Pattern input_pattern;
private Matcher matcher;
    private static HashMap<String, PrintWriter> users = null;
    private Socket socket = null;
private PrintWriter output;

    public ServerThread(Socket socket, HashMap<String, PrintWriter> userEntries) throws IOException {
        super("ServerThread");
        this.socket = socket;
this.output = new PrintWriter(socket.getOutputStream(), true);
        users = userEntries;
    }

    public void run() {

        try (
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()))
        ) {
            String inputLine, outputLine, username;

while ( !  (matcher = userName_pattern.matcher( in.readLine())).find())
	{output.println("sorry, usernames can only contain letters and numbers");
	output.println("Please enter another username> ");
	}
username = matcher.group(1);
            this.output.println("You entered: " + username);
            users.put(username, this.output);

            while ((inputLine = in.readLine()) != null) {
	matcher = input_pattern.matcher(inputLine);
                outputLine = inputLine;
                if(matcher.find() ){ //the line starts with a slash
		String cmd = matcher.group(1);
		                    String user = matcher.group(2);
		String message = matcher.group(3);
                    users.get(user).println(username + ": " + message);
                } else {
                    for(Map.Entry<String, PrintWriter> entry : users.entrySet()){
                        if(!entry.getKey().equals(username))
                            entry.getValue().println(username + ": " + outputLine);
                    }
                }

                if (outputLine.equals("Bye"))
                    break;
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

