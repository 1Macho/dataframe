package com.jpossaz.dataframe;

import com.jpossaz.dataframe.datavalues.DataValue;
import com.jpossaz.dataframe.datavalues.NumericDataValue;
import com.jpossaz.dataframe.datavalues.TextDataValue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class DataFrame implements List<Registry>, Cloneable {
    private String dataFrameName;
    private List<Registry> registryList = new ArrayList<>();
    private String watchedValue;
    private DataSignature signature;

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
            Registry newRegistry = baseRegistry.clone();
            String[] lineBits = Utils.parseDataFrameLine(fileLines.get(i));
            for (int j = 0; j < lineBits.length; j++)
            {
                newRegistry.setValue(((DataValue)signature.get(j)).getName(), lineBits[j]);
            }
            add(newRegistry);
        }
    }

    public DataFrame (String dataFrameName, DataSignature signature)
    {
        this.dataFrameName = dataFrameName;
        this.signature = signature;
    }

    public String getWatchedValue ()
    {
        return watchedValue;
    }

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
