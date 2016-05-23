/*taken from: http://www.tutorialspoint.com/java/java_regular_expressions.htm

The matches and lookingAt Methods:

The matches and lookingAt methods both attempt to match an input sequence against a pattern. The difference, however, is that matches requires the entire input sequence to be matched, while lookingAt does not.

Both methods always start at the beginning of the input string. Here is the example explaining the functionality:
*/

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class lookingAt_or_matches
{
    private static final String REGEX = "foo";
    private static final String INPUT = "foo";//ooooooooooooooo";
    private static Pattern pattern;
    private static Matcher matcher;

    public static void main( String args[] ){
       pattern = Pattern.compile(REGEX);
       matcher = pattern.matcher(INPUT);

       System.out.println("Current REGEX is: "+REGEX);
       System.out.println("Current INPUT is: "+INPUT);

       System.out.println("matches(): "+matcher.matches());
       System.out.println("lookingAt(): "+matcher.lookingAt());

   }
}