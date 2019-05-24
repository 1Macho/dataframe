package com.jpossaz.dataframe.datavalues;

/**
 * Class that extends DataValue and supports the implementation of textual values within the DataFrame.
 */
public final class TextDataValue extends DataValue {

    /**
     * The value stored inside this DataValue.
     */
    private String value = "";
    /**
     * The name of this DataValue.
     */
    private String name;
    /**
     * Whether or not this DataValue is set to the default.
     */
    private boolean isSet = false;

    /**
     * Class constructor that only uses a name for the TextDataValue creation.
     * @param name The name to be assigned to this DataValue.
     */
    public TextDataValue (String name)
    {
        this.name = name;
    }
    /**
     * Constructor that uses the name and a value as a string.
     * @param name The name to be assigned to this DataValue.
     * @param value A string that holds the representation of the value to be stored.
     */
    public TextDataValue (String name, String value)
    {
        this.name = name;
        this.value = value;
        verifyValue();
    }

    /**
     * Determines whether or not this value is set to the default.
     * @return True if the value is not the default. False otherwise.
     */
    @Override
    public boolean getIsSet ()
    {
        return isSet;
    }

    /**
     * Verify that the value is not set to the default value.
     */
    private void verifyValue ()
    {
        isSet = (value.equals(""));
    }

    /**
     * Clone this DataValue.
     * @return A clone of this DataValue.
     */
    @Override
    public DataValue clone() throws ClassCastException {
        return new TextDataValue(this.name, this.value);
    }

    /**
     * Get the value stored within this DataValue.
     * @return The value stored within this DataValue.
     */
    @Override
    public Object getValue() {
        return value;
    }

    /**
     * Set the value stored in this DataValue.
     * @param value The value to be stored inside the DataValue.
     * @throws ClassCastException When the value provided cannot fit into this type of DataValue.
     */
    @Override
    public void setValue(Object value) throws ClassCastException {
        this.value = (String)value;
        verifyValue();
    }

    /**
     * Obtain the name of this DataValue.
     * @return The name of this DataValue.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Get the type of DataValue.
     * @return The type of DataValue.
     */
    @Override
    public String getType() {
        return "text";
    }

    /**
     * Compare this DataValue with another one.
     * @param o The object that this will be compared to.
     * @return 1 if greater, 0 if equal, -1 if lesser.
     * @throws ClassCastException When the object provided is not comparable with this one.
     */
    @Override
    public int compareTo(Object o) throws ClassCastException {
        TextDataValue other = (TextDataValue) o;
        String cachedValueOther = (String)other.getValue();
        String cachedValueSelf = (String)getValue();
        return cachedValueSelf.compareTo(cachedValueOther);
    }

    /**
     * Verify if an arbitrary object would fit into a DataValue of this type.
     * @param o The object to be considered.
     * @return True if the object would fit. False otherwise.
     */
    public static boolean wouldFit (Object o)
    {
        try {
            String test = (String)o;
        }
        catch (Exception e)
        {
            return false;
        }
        return true;
    }
}
