
package com.jpossaz.dataframe;





import java.util.ArrayList;
import java.util.List;

/**
 * A class containing various utilities used around the program.
 */
public class Utils {
    /**
     * Parse one line of the raw data frame text and turn it into useful data.
     * @param line The line to be parsed.
     * @return An array of the bits that were extracted from the line.
     */
    public static String[] parseDataFrameLine(String line)
    {
        line = line + "\n";
        line = line.replace(",,", ",\"NOVALUEFORTHISSEGMENT\",");
        line = line.replace(",\n", ",\"NOVALUEFORTHISSEGMENT\"");
        String[] splitByQuotes = line.split("\"");
        List<String> result = new ArrayList<>();
        for(int i = 1; i < splitByQuotes.length; i+=2)
        {
            if (splitByQuotes[i].equals("NOVALUEFORTHISSEGMENT"))
            {
                result.add("");
                continue;
            }
            result.add(splitByQuotes[i]);
        }
        return result.toArray(new String[result.size()]);
    }

    /**
     * Split a REPL command line into the segments that compose it.
     * @param line The line to be considered.
     * @return An array of the command segments.
     */
    public static String[] splitCommandLine(String line)
    {
        ArrayList<String> commandBits = new ArrayList<>();
        boolean splitOnSpace = true;
        String currentBit = "";
        for (int i = 0; i < line.length(); i++)
        {
            if ((line.charAt(i) == ' ' && splitOnSpace) || i == line.length() - 1) {
                if (i == line.length() - 1) currentBit = currentBit + line.charAt(i);
                commandBits.add(currentBit);
                currentBit = "";
                continue;
            }
            if (line.charAt(i) == '"')
            {
                splitOnSpace = false;
                continue;
            }
            currentBit = currentBit + line.charAt(i);
        }
        return commandBits.toArray(new String[commandBits.size()]);
    }
}
