package com.jpossaz.dataframe;

import com.jpossaz.dataframe.datavalues.DataValue;

import java.util.HashMap;

/**
 * A class that holds an ordered set of DataValues according to a signature.
 */
public class Registry implements Comparable {
    /**
     * The DataFrame that owns this registry.
     */
    private DataFrame parent;
    /**
     * A map of names to values according to the signature.
     */
    public HashMap<String, DataValue> values = new HashMap<>();
    /**
     * A description of the columns in this registry.
     */
    private DataSignature signature;
    /**
     * The last clone that was created of this registry.
     */
    public Registry lastClone;

    /**
     * Create a registry as a clone of another one.
     * @param cloneBase The base registry to clone.
     * @param parent The parent of the new registry.
     */
    public Registry(Registry cloneBase, DataFrame parent) {
        this.parent = parent;
        for (String key : cloneBase.values.keySet()) {
            this.values.put(key, cloneBase.values.get(key).clone());
        }
        this.signature = cloneBase.signature;
        cloneBase.lastClone = this;
    }

    /**
     * Create a new registry with a given signature.
     * @param signature The signature to use for the new registry.
     * @param parent The parent of the new registry.
     * @throws CloneNotSupportedException Shouldn't happen.
     */
    public Registry (DataSignature signature, DataFrame parent) throws CloneNotSupportedException {
        this.signature = signature;
        this.parent = parent;
        for (int i = 0; i < signature.size(); i++)
        {

            DataValue newDataValue = (this.signature.get(i)).clone();
            values.put(newDataValue.getName(), newDataValue);
        }
    }

    /**
     * Set an arbitrary value on the registry.
     * @param name The name of the value to modify.
     * @param value The value to set.
     */
    public void setValue(String name, Object value)
    {
        values.get(name).setValue(value);
    }

    /**
     * Compare this registry to another one.
     * @param o The registry to compare to.
     * @return 1 if greater, 0 if equal, -1 if lesser.
     * @throws ClassCastException when the object passed is not a registry.
     */
    @Override
    public int compareTo(Object o) throws ClassCastException {
        DataValue watchedValueSelf = obtainWatchedValue();
        DataValue watchedValueOther = ((Registry)o).obtainWatchedValue();
        return watchedValueSelf.compareTo(watchedValueOther);
    }

    /**
     * Obtain the value currently being watched.
     * @return The value currently being watched.
     */
    public DataValue obtainWatchedValue ()
    {
        String watchedValueName = parent.getWatchedValue();
        return values.get(watchedValueName);
    }
}
