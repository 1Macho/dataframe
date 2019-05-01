package com.jpossaz.dataframe.datavalues;

public class NumericDataValue extends DataValue {

    private Double value = Double.NaN;
    private String name;

    public NumericDataValue (String name)
    {
        this.name = name;
    }
    public NumericDataValue (String name, double value)
    {
        this.name = name;
        this.value = value;
    }
    public NumericDataValue (String name, String value)
    {
        this.name = name;
        this.value = Double.parseDouble(value);
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) throws ClassCastException {
        try {
            this.value = (double) value;
        }
        catch (ClassCastException c)
        {
            try {
                this.value = Double.parseDouble((String) value);
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
