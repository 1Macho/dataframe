
package com.jpossaz.dataframe;


public class DataOperation {
    /*public static Double obtainAverage (DataFrame input)
    {
        if(!input.watchingNumericValue())
            throw new ClassCastException("The watched value is non-numeric.");
        
    }*/


    public static Double obtainMinimum (DataFrame input) throws ClassCastException
    {
        if(!input.watchingNumericValue())
            throw new ClassCastException("The watched value is non-numeric.");
        Double maximumValue = Double.MIN_VALUE;
        for (Registry reg : input)
        {
            Double thisValue = (Double)reg.obtainWatchedValue().getValue();
            if (thisValue > maximumValue)
            {
                maximumValue = thisValue;
            }
        }
        return maximumValue;
    }

    public static Double obtainMaximum (DataFrame input) throws ClassCastException
    {
        if(!input.watchingNumericValue())
            throw new ClassCastException("The watched value is non-numeric.");
        Double minimumValue = Double.MAX_VALUE;
        for (Registry reg : input)
        {
            Double thisValue = (Double)reg.obtainWatchedValue().getValue();
            if (thisValue < minimumValue)
            {
                minimumValue = thisValue;
            }
        }
        return minimumValue;
    }

    public static Integer obtainCount (DataFrame input)
    {
        int count = 0;
        for (Registry reg : input)
        {
            if (reg.obtainWatchedValue().getIsSet())
            {
                count++;
            }
        }
        return count;
    }
}
