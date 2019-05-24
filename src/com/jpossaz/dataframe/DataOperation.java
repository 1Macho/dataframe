
package com.jpossaz.dataframe;


import java.util.ArrayList;
import java.util.List;

/**
 * A class that contains various statistical operations that can be carried out inside a DataFrame.
 */
public class DataOperation {

    /**
     * Obtain the trend of a DataFrame.
     * @param input The DataFrame in which the calculation is carried out.
     * @return The trend of the DataFrame.
     */
    public static Object obtainTrend (DataFrame input)
    {
        List<Object> verifiedObjects = new ArrayList<>();
        Object currentTrend = null;
        int currentTrendCount = 0;
        for (Registry a : input)
        {
            if (!verifiedObjects.contains(a))
            {
                int localCount = 0;
                for (Registry b : input)
                {
                    if(a.equals(b))
                    {
                        localCount++;
                    }
                }
                if (localCount > currentTrendCount)
                {
                    currentTrend = a;
                    currentTrendCount = localCount;
                }
                verifiedObjects.add(a);
            }
        }
        if (currentTrend != null) {
            return ((Registry) currentTrend).obtainWatchedValue().getValue();
        } else return null;
    }

    /**
     * Obtain the standard deviation of a DataFrame.
     * @param input The DataFrame in which the calculation is carried out.
     * @return The numerical standard deviation of the DataFrame.
     */
    public static Double obtainStandardDeviation (DataFrame input)
    {
        if(!input.watchingNumericValue())
            throw new ClassCastException("The watched value is non-numeric.");
        Double mean = obtainMean(input);
        Double accumulation = 0.0d;
        int count = 0;
        for (Registry reg : input)
        {
            if (reg.obtainWatchedValue().getIsSet())
            {
                Double thisValue = (Double)reg.obtainWatchedValue().getValue();
                Double delta = mean - thisValue;
                accumulation += delta * delta;
                count++;
            }
        }
        Double variance = accumulation / count;
        return Math.sqrt(variance);
    }

    /**
     * Obtain the trend mean of a DataFrame.
     * @param input The DataFrame in which the calculation is carried out.
     * @return The numerical mean of the DataFrame.
     */
    public static Double obtainMean (DataFrame input)
    {
        if(!input.watchingNumericValue())
            throw new ClassCastException("The watched value is non-numeric.");
        Double accumulation = 0.0d;
        int count = 0;
        for (Registry reg : input)
        {
            if (reg.obtainWatchedValue().getIsSet())
            {
                accumulation += (Double)reg.obtainWatchedValue().getValue();
                count++;
            }
        }
        return accumulation / count;
    }

    /**
     * Obtain the maximum of a DataFrame.
     * @param input The DataFrame in which the calculation is carried out.
     * @return The numerical maximum of the DataFrame.
     */
    public static Double obtainMaximum(DataFrame input) throws ClassCastException
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

    /**
     * Obtain the minimum of a DataFrame.
     * @param input The DataFrame in which the calculation is carried out.
     * @return The numerical minimum of the DataFrame.
     */
    public static Double obtainMinimum(DataFrame input) throws ClassCastException
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

    /**
     * Obtain the count of a DataFrame.
     * @param input The DataFrame in which the calculation is carried out.
     * @return The numerical count of the DataFrame.
     */
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
