package com.jpossaz.dataframe;

import com.jpossaz.dataframe.datavalues.DataValue;

import java.util.HashMap;

public class Registry implements Comparable, Cloneable {
    private DataFrame parent;
    private HashMap<String, DataValue> values = new HashMap<>();
    private DataSignature signature;

    public Registry (DataSignature signature, DataFrame parent) throws CloneNotSupportedException {
        this.signature = signature;
        this.parent = parent;
        for (int i = 0; i < signature.size(); i++)
        {

            DataValue newDataValue = (this.signature.get(i)).clone();
            values.put(newDataValue.getName(), newDataValue);
        }
    }

    public void setValue(String name, Object value)
    {
        values.get(name).setValue(value);
    }

    @Override
    public int compareTo(Object o) throws ClassCastException {
        DataValue watchedValueSelf = obtainWatchedValue();
        DataValue watchedValueOther = ((Registry)o).obtainWatchedValue();
        return watchedValueSelf.compareTo(watchedValueOther);
    }
    public DataValue obtainWatchedValue ()
    {
        String watchedValueName = parent.getWatchedValue();
        return values.get(watchedValueName);
    }
    public Registry clone () throws CloneNotSupportedException
    {
        Registry clone = (Registry) super.clone();
        return clone;
    }
}
