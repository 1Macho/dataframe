package com.jpossaz.dataframe.datavalues;

public class NumericDataValue extends DataValue {

    private Double value = Double.NaN;
    private String name;
    private boolean isSet = false;

    public NumericDataValue (String name)
    {
        this.name = name;
    }

    private void verifyValue ()
    {
        isSet = (value == Double.NaN);
    }

    public NumericDataValue (String name, double value)
    {
        this.name = name;
        this.value = value;
        verifyValue();
    }
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

    @Override
    public boolean getIsSet () { return isSet; }

    @Override
    public Object getValue() {
        return value;
    }

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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getType() {
        return "numeric";
    }

    @Override
    public int compareTo(Object o) throws ClassCastException {
        NumericDataValue other = (NumericDataValue) o;
        Double cachedValueOther = (Double)other.getValue();
        Double cachedValueSelf = (Double)getValue();
        return cachedValueSelf.compareTo(cachedValueOther);
    }

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
