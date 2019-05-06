package com.jpossaz.dataframe.datavalues;

public abstract class DataValue implements Comparable {
    public abstract DataValue clone();
    public abstract Object getValue ();
    public abstract void setValue (Object value);
    public abstract String getName();
    public abstract String getType();
    public abstract boolean getIsSet();
}
