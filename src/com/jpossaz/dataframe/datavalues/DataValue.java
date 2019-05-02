package com.jpossaz.dataframe.datavalues;

public abstract class DataValue implements Comparable, Cloneable {
    public abstract Object getValue ();
    public abstract void setValue (Object value);
    public abstract String getName();
    public abstract String getType();
    public DataValue clone () throws CloneNotSupportedException
    {
        DataValue clone = (DataValue)super.clone();
        return clone;
    }
    public abstract boolean getIsSet();
}
