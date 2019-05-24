package com.jpossaz.dataframe.datavalues;

/**
 * Class that extends DataValue and supports the implementation of numeric values within the DataFrame.
 */
public final class NumericDataValue extends DataValue {

    /**
     * The value stored inside this DataValue.
     */
    private double value = Double.NaN;
    /**
     * The name of this DataValue.
     */
    private String name;
    /**
     * Whether or not this DataValue is set to the default.
     */
    private boolean isSet = false;


    /**
     * Class constructor that only uses a name for the NumericDataValue creation.
     * @param name The name to be assigned to this DataValue.
     */
    public NumericDataValue (String name)
    {
        this.name = name;
    }

    /**
     * Verify that the value is not set to the default value.
     */
    private void verifyValue ()
    {
        isSet = !Double.isNaN(value);
    }

    /**
     * Constructor that utilizes both the name and the value.
     * @param name The name to be assigned to this DataValue.
     * @param value The value to be assigned to this DataValue.
     */
    public NumericDataValue (String name, double value)
    {
        this.name = name;
        this.value = value;
        verifyValue();
    }

    /**
     * Constructor that uses the name and a value as a string that will be parsed.
     * @param name The name to be assigned to this DataValue.
     * @param value A string that (potentially) holds the representation of the value to be stored.
     */
    public NumericDataValue (String name, String value)
    {
        this.name = name;
        if (value.equals(""))
        {
            this.value = Double.NaN;
            return;
        }
        this.value = Double.parseDouble(value);
        verifyValue();
    }

    /**
     * Determines whether or not this value is set to the default.
     * @return True if the value is not the default. False otherwise.
     */
    @Override
    public boolean getIsSet () { return isSet; }

    /**
     * Clone this DataValue.
     * @return A clone of this DataValue.
     */
    @Override
    public DataValue clone() {
        return new NumericDataValue(name, value);
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
        if (value.equals(""))
        {
            this.value = Double.NaN;
            verifyValue();
            return;
        }
        try {
            this.value = (double) value;
            verifyValue();
        }
        catch (ClassCastException c)
        {
            try {
                this.value = Double.parseDouble((String) value);
                verifyValue();
            }
            catch (Exception e)
            {
                throw new ClassCastException();
            }
        }
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
        return "numeric";
    }

    /**
     * Compare this DataValue with another one.
     * @param o The object that this will be compared to.
     * @return 1 if greater, 0 if equal, -1 if lesser.
     * @throws ClassCastException When the object provided is not comparable with this one.
     */
    @Override
    public int compareTo(Object o) throws ClassCastException {
        NumericDataValue other = (NumericDataValue) o;
        Double cachedValueOther = (Double)other.getValue();
        Double cachedValueSelf = (Double)getValue();
        return cachedValueSelf.compareTo(cachedValueOther);
    }

    /**
     * Verify if an arbitrary object would fit into a DataValue of this type.
     * @param o The object to be considered.
     * @return True if the object would fit. False otherwise.
     */
    public static boolean wouldFit (Object o)
    {
        if (o.equals("")) return true;
        try {
            Double test = (Double)o;
        }
        catch (Exception e)
        {
            try {
                Double test = Double.parseDouble((String)o);
            }
            catch (Exception f)
            {
                return false;
            }
        }
        return true;
    }
}
