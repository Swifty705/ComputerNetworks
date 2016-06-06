/* taken from https://docs.oracle.com/javase/tutorial/essential/regex/pse.html
*/

import java.io.Console;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.regex.PatternSyntaxException;

public class Regex {

    public static void main(String[] args){
        Pattern pattern = null;
        Matcher matcher = null;

        Console con = System.console();
        if (con == null) {
            System.err.println("No console.");
            System.exit(1);
        }


            try{
                pattern = Pattern.compile(con.readLine("%nEnter your regex: "));
}
            catch(PatternSyntaxException pse){
                con.format("There is a problem" +
                               " with the regular expression!%n");
                con.format("The pattern in question is: %s%n",
                               pse.getPattern());
                con.format("The description is: %s%n",
                               pse.getDescription());
                con.format("The message is: %s%n",
                               pse.getMessage());
                con.format("The index is: %s%n",
                               pse.getIndex());
                System.exit(0);
            }

while ( true)
	{matcher = pattern.matcher(con.readLine("Enter text> "));
            
	boolean foundAtLeastOne = false;
	while (matcher.find()) 
		{con.format("found the text" +
		                    " \"%s\" starting at " +
		                    "index %d and ending at index %d.%n",
		                    matcher.group(),
		                    matcher.start(),
		                    matcher.end());
		                foundAtLeastOne = true;
		
		for ( int i = 1 ; i <= matcher.groupCount() ; i++)
			con.format("group#"+i+" "+
		                    " \"%s\" starting at " +
		                    "index %d and ending at index %d.%n",
		                    matcher.group(i),
		                    matcher.start(i),
		                    matcher.end(i));
		
			
		            }//end while finding matches
            if(!foundAtLeastOne){
                con.format("No match found.%n");
            }
    }//end while true 
}//end main
}