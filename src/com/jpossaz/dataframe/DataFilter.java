package com.jpossaz.dataframe;

public class DataFilter {
    public static DataFrame filterLessThan (DataFrame input, Comparable filterValue) throws CloneNotSupportedException {
        DataFrame result = input.clone();
        for (Registry r : input)
        {
            Comparable thisValue = (Comparable)r.obtainWatchedValue().getValue();
            if (thisValue.compareTo(filterValue) > 0)
            {
                result.remove(r);
            }
        }
        return result;
    }
    public static DataFrame filterGreaterThan (DataFrame input, Comparable filterValue) throws CloneNotSupportedException {
        DataFrame result = input.clone();
        for (Registry r : input)
        {
            Comparable thisValue = (Comparable)r.obtainWatchedValue().getValue();
            if (thisValue.compareTo(filterValue) < 0)
            {
                result.remove(r);
            }
        }
        return result;
    }
    public static DataFrame filterLessThanOrEqualTo (DataFrame input, Comparable filterValue) throws CloneNotSupportedException {
        DataFrame result = input.clone();
        for (Registry r : input)
        {
            Comparable thisValue = (Comparable)r.obtainWatchedValue().getValue();
            if (thisValue.compareTo(filterValue) >= 0)
            {
                result.remove(r);
            }
        }
        return result;
    }
    public static DataFrame filterGreaterThanOrEqualTo (DataFrame input, Comparable filterValue) throws CloneNotSupportedException {
        DataFrame result = input.clone();
        for (Registry r : input)
        {
            Comparable thisValue = (Comparable)r.obtainWatchedValue().getValue();
            if (thisValue.compareTo(filterValue) <= 0)
            {
                result.remove(r);
            }
        }
        return result;
    }
    public static DataFrame filterEqualTo (DataFrame input, Comparable filterValue) throws CloneNotSupportedException {
        DataFrame result = input.clone();
        for (Registry r : input)
        {
            Comparable thisValue = (Comparable)r.obtainWatchedValue().getValue();
            if (thisValue.compareTo(filterValue) == 0)
            {
                result.remove(r);
            }
        }
        return result;
    }
    public static DataFrame filterNotEqualTo (DataFrame input, Comparable filterValue) throws CloneNotSupportedException {
        DataFrame result = input.clone();
        for (Registry r : input)
        {
            Comparable thisValue = (Comparable)r.obtainWatchedValue().getValue();
            if (thisValue.compareTo(filterValue) != 0)
            {
                result.remove(r);
            }
        }
        return result;
    }
}
