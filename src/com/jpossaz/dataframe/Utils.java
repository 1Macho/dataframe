package com.jpossaz.dataframe;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static String[] parseDataFrameLine(String line)
    {
        line = line.replace(",,", ",\"\",");
        line = line.replace(",\n", ",\"\"");
        String[] splitByQuotes = line.split("\"");
        List<String> result = new ArrayList<>();
        for(int i = 1; i < splitByQuotes.length; i+=2)
        {
            result.add(splitByQuotes[i]);
        }
        return result.toArray(String[]::new);
    }
}
