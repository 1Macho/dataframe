package com.jpossaz.dataframe.datavalues;

public class TextDataValue extends DataValue {

    private String value = "";
    private String name;
    private boolean isSet = false;

    public TextDataValue (String name)
    {
        this.name = name;
    }
    public TextDataValue (String name, String value)
    {
        this.name = name;
        this.value = value;
        verifyValue();
    }

    @Override
    public boolean getIsSet ()
    {
        return isSet;
    }

    private void verifyValue ()
    {
        isSet = (value.equals(""));
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) throws ClassCastException {
        this.value = (String)value;
        verifyValue();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getType() {
        return "text";
    }

    @Override
    public int compareTo(Object o) throws ClassCastException {
        TextDataValue other = (TextDataValue) o;
        String cachedValueOther = (String)other.getValue();
        String cachedValueSelf = (String)getValue();
        return cachedValueSelf.compareTo(cachedValueOther);
    }

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
