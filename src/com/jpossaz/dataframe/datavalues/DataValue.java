package com.jpossaz.dataframe.datavalues;

/**
 * This is the abstract class that defines a data value within the dataframe.
 */
public abstract class DataValue implements Comparable {

    /**
     * This method creates a copy of a DataValue that is essentially the same value on a different memory location.
     * @return The clones DataValue.
     */
    public abstract DataValue clone();

    /**
     * Obtain an explicit representation of the value stored.
     * @return The object stored inside the DataValue.
     */
    public abstract Object getValue ();

    /**
     * Set the value stored inside the DataValue.
     * @param value The value to be stored inside the DataValue.
     */
    public abstract void setValue (Object value);

    /**
     * Get the name of this DataValue.
     * @return The name of the DataValue.
     */
    public abstract String getName();

    /**
     * Get the type of this DataValue.
     * @return A string describing the type of this DataValue.
     */
    public abstract String getType();

    /**
     * Determine whether or not this DataValue is set to a value other than the default.
     * @return True if the DataValue contains a "set" value. False otherwise.
     */
    public abstract boolean getIsSet();
}
