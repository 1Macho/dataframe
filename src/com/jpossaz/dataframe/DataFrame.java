package com.jpossaz.dataframe;

import com.jpossaz.dataframe.datavalues.DataValue;
import com.jpossaz.dataframe.datavalues.NumericDataValue;
import com.jpossaz.dataframe.datavalues.TextDataValue;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * A class that describes a DataFrame.
 */
public class DataFrame implements List<Registry> {

    /**
     * The name of this DataFrame.
     */
    private String dataFrameName;
    /**
     * The list containing all the registries.
     */
    private List<Registry> registryList = new ArrayList<>();
    /**
     * The value that is currently being watched inside the DataFrame.
     */
    private String watchedValue;
    /**
     * The signature of this DataFrame (I,e: The names and types of each columns).
     */
    private DataSignature signature;

    /**
     * Set the watched value within the DataFrame.
     * @param watchedValue The new value to be watched within the DataFrame.
     */
    public void setWatchedValue(String watchedValue) {
        this.watchedValue = watchedValue;
    }

    /**
     * A constructor method that creates a clone of the passed one.
     * @param cloneBase The DataFrame to be cloned.
     */
    public DataFrame(DataFrame cloneBase) {
        this.dataFrameName = cloneBase.dataFrameName;
        for (Registry reg : cloneBase.registryList) {
            Registry newReg = new Registry(reg, this);
            this.registryList.add(newReg);
        }
        this.watchedValue = cloneBase.watchedValue;
        this.signature = cloneBase.signature;
    }

    /**
     * Create a DataFrame from a file path (load it into memory from disk).
     * @param filepath The path where the DataFrame resides on disk.
     * @throws IOException When a file operation could not be carried out.
     * @throws IndexOutOfBoundsException When the signature of the DataFrame does not match the rest of the data.
     * @throws CloneNotSupportedException Shouldn't happen.
     */
    public DataFrame (String filepath) throws IOException, IndexOutOfBoundsException, CloneNotSupportedException {
        String[] filepathSplitBySlash = filepath.split("/");
        String lastBit = filepathSplitBySlash[filepathSplitBySlash.length - 1];
        int pos = lastBit.lastIndexOf('.');
        if (pos == -1) dataFrameName = lastBit;
        else this.dataFrameName = lastBit.substring(0, pos);
        List<String> fileLines = Files.readAllLines(Paths.get(filepath));
        String[] firstLine = Utils.parseDataFrameLine(fileLines.get(0));
        String[] secondLine = Utils.parseDataFrameLine(fileLines.get(1));
        if (firstLine.length != secondLine.length)
            throw new IndexOutOfBoundsException("File signature does not match the data.");
        signature = new DataSignature();
        for (int i = 0; i < firstLine.length; i++)
        {
            if(NumericDataValue.wouldFit(secondLine[i]))
            {
                signature.add(new NumericDataValue(firstLine[i]));
                if (watchedValue == null)
                {
                    watchedValue = firstLine[i];
                }
                continue;
            }
            if(TextDataValue.wouldFit(secondLine[i]))
            {
                signature.add(new TextDataValue(firstLine[i]));
                continue;
            }
        }
        Registry baseRegistry = new Registry(signature, this);
        for (int i = 1; i < fileLines.size(); i++)
        {
            Registry newRegistry = new Registry(baseRegistry, this);
            String[] lineBits = Utils.parseDataFrameLine(fileLines.get(i));
            for (int j = 0; j < lineBits.length; j++)
            {
                newRegistry.setValue(signature.get(j).getName(), lineBits[j]);
            }
            add(newRegistry);
        }
    }

    /**
     * Save the DataFrame to disk.
     * @param filename The file path where the DataFrame will be saved to.
     * @throws IOException When a file operation could not be carried out.
     */
    public void saveDataFrame (String filename) throws IOException {
        filename = filename + ".csv";
        StringBuilder builder = new StringBuilder();
        for (int line = -1; line < size(); line++) {
            System.out.println("Wrote " + line + " lines...");
            for (int i = 0; i < signature.size(); i++) {
                if (line == -1) {
                    builder.append("\"" + signature.get(i).getName() + "\"");
                }
                else
                {
                    builder.append("\"" + get(line).values.get(signature.get(i).getName()).getValue() + "\"");

                }
                if (i != signature.size() - 1) {
                    builder.append(",");
                }
                else
                {
                    builder.append("\n");
                }
            }
        }
        FileWriter writer = new FileWriter(filename);
        writer.write(builder.toString());
        writer.close();
    }

    /**
     * Set the name of this DataFrame.
     * @param name The name to be set on the DataFrame.
     */
    public void setDataFrameName (String name)
    {
        this.dataFrameName = name;
    }

    /**
     * Get the name of this DataFrame.
     * @return The name of the DataFrame.
     */
    public String getDataFrameName ()
    {
        return dataFrameName;
    }

    /**
     * A constructor that only takes a name and a signature, essentially creating a defined frame with no values.
     * @param dataFrameName The name of the DataFrame.
     * @param signature The signature of the DataFrame.
     */
    public DataFrame (String dataFrameName, DataSignature signature)
    {
        this.dataFrameName = dataFrameName;
        this.signature = signature;
    }

    /**
     * Obtain the number of columns that this DataFrame has.
     * @return The number of columns.
     */
    public int getColumnCount () { return signature.size(); }

    /**
     * Obtain the value being watched.
     * @return The value being watched.
     */
    public String getWatchedValue ()
    {
        return watchedValue;
    }

    /**
     * Get the names of the columns on this DataFrame.
     * @return The names of the columns.
     */
    public String[] getColumnNames() {
        ArrayList<String> columns = new ArrayList<>();
        for (DataValue value : signature) {
            columns.add(value.getName());
        }
        return columns.toArray(new String[columns.size()]);
    }

    /**
     * Verify whether or not this DataFrame contains values that are numerical.
     * @return True if the DataValue contains numerical values. False otherwise.
     */
    public boolean watchingNumericValue (){
        for (DataValue value: signature)
        {
            if (value.getName().equals(getWatchedValue()))
            {
                try {
                    NumericDataValue test = (NumericDataValue)value;
                    return true;
                }
                catch (Exception e)
                {
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public int size() {
        return registryList.size();
    }

    @Override
    public boolean isEmpty() {
        return registryList.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return registryList.contains(o);
    }

    @Override
    public Iterator<Registry> iterator() {
        return registryList.iterator();
    }

    @Override
    public Object[] toArray() {
        return registryList.toArray();
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        return registryList.toArray(ts);
    }

    @Override
    public boolean add(Registry registry) {
        return registryList.add(registry);
    }

    @Override
    public boolean remove(Object o) {
        return registryList.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return registryList.containsAll(collection);
    }

    @Override
    public boolean addAll(Collection<? extends Registry> collection) {
        return registryList.containsAll(collection);
    }

    @Override
    public boolean addAll(int i, Collection<? extends Registry> collection) {
        return registryList.addAll(i, collection);
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return registryList.removeAll(collection);
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return registryList.retainAll(collection);
    }

    @Override
    public void clear() {
        registryList.clear();
    }

    @Override
    public Registry get(int i) {
        return registryList.get(i);
    }

    @Override
    public Registry set(int i, Registry registry) {
        return registryList.set(i, registry);
    }

    @Override
    public void add(int i, Registry registry) {
        registryList.add(i, registry);
    }

    @Override
    public Registry remove(int i) {
        return registryList.remove(i);
    }

    @Override
    public int indexOf(Object o) {
        return registryList.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return registryList.lastIndexOf(o);
    }

    @Override
    public ListIterator<Registry> listIterator() {
        return registryList.listIterator();
    }

    @Override
    public ListIterator<Registry> listIterator(int i) {
        return registryList.listIterator(i);
    }

    @Override
    public List<Registry> subList(int i, int i1) {
        return registryList.subList(i, i1);
    }
}
