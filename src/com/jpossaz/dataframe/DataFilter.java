package com.jpossaz.dataframe;

/**
 * A class that implements various methods for filtering DataFrames.
 */
public class DataFilter {
    /**
     * Filter the DataFrame considering the values that are less than the filterValue.
     * @param input The DataFrame to be filtered.
     * @param filterValue The value used as a filter.
     * @return A filtered copy of the original DataFrame.
     */
    public static DataFrame filterLessThan (DataFrame input, Comparable filterValue) {
        DataFrame result = new DataFrame(input);
        for (Registry r : input)
        {
            Comparable thisValue = (Comparable)r.obtainWatchedValue().getValue();
            if (thisValue.compareTo(filterValue) > 0)
            {
                result.remove(r.lastClone);
            }
        }
        return result;
    }
    /**
     * Filter the DataFrame considering the values that are greater than the filterValue.
     * @param input The DataFrame to be filtered.
     * @param filterValue The value used as a filter.
     * @return A filtered copy of the original DataFrame.
     */
    public static DataFrame filterGreaterThan (DataFrame input, Comparable filterValue) {
        DataFrame result = new DataFrame(input);
        for (Registry r : input)
        {
            Comparable thisValue = (Comparable)r.obtainWatchedValue().getValue();
            if (thisValue.compareTo(filterValue) < 0)
            {
                result.remove(r.lastClone);
            }
        }
        return result;
    }
    /**
     * Filter the DataFrame considering the values that are less than or equal to the filterValue.
     * @param input The DataFrame to be filtered.
     * @param filterValue The value used as a filter.
     * @return A filtered copy of the original DataFrame.
     */
    public static DataFrame filterLessThanOrEqualTo (DataFrame input, Comparable filterValue) {
        DataFrame result = new DataFrame(input);
        for (Registry r : input)
        {
            Comparable thisValue = (Comparable)r.obtainWatchedValue().getValue();
            if (thisValue.compareTo(filterValue) >= 0)
            {
                result.remove(r.lastClone);
            }
        }
        return result;
    }
    /**
     * Filter the DataFrame considering the values that are greater than or equal to the filterValue.
     * @param input The DataFrame to be filtered.
     * @param filterValue The value used as a filter.
     * @return A filtered copy of the original DataFrame.
     */
    public static DataFrame filterGreaterThanOrEqualTo (DataFrame input, Comparable filterValue) {
        DataFrame result = new DataFrame(input);
        for (Registry r : input)
        {
            Comparable thisValue = (Comparable)r.obtainWatchedValue().getValue();
            if (thisValue.compareTo(filterValue) <= 0)
            {
                result.remove(r.lastClone);
            }
        }
        return result;
    }
    /**
     * Filter the DataFrame considering the values that are equal to the filterValue.
     * @param input The DataFrame to be filtered.
     * @param filterValue The value used as a filter.
     * @return A filtered copy of the original DataFrame.
     */
    public static DataFrame filterEqualTo (DataFrame input, Comparable filterValue) {
        DataFrame result = new DataFrame(input);
        for (Registry r : input)
        {
            Comparable thisValue = (Comparable)r.obtainWatchedValue().getValue();
            if (thisValue.compareTo(filterValue) == 0)
            {
                result.remove(r.lastClone);
            }
        }
        return result;
    }
    /**
     * Filter the DataFrame considering the values that are not equal to the filterValue.
     * @param input The DataFrame to be filtered.
     * @param filterValue The value used as a filter.
     * @return A filtered copy of the original DataFrame.
     */
    public static DataFrame filterNotEqualTo (DataFrame input, Comparable filterValue) {
        DataFrame result = new DataFrame(input);
        for (Registry r : input)
        {
            Comparable thisValue = (Comparable)r.obtainWatchedValue().getValue();
            if (thisValue.compareTo(filterValue) != 0)
            {
                result.remove(r.lastClone);
            }
        }
        return result;
    }
}
