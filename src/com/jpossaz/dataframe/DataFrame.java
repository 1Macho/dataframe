package com.jpossaz.dataframe;

import com.jpossaz.dataframe.datavalues.DataValue;
import com.jpossaz.dataframe.datavalues.NumericDataValue;
import com.jpossaz.dataframe.datavalues.TextDataValue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class DataFrame implements Collection<Registry>, Cloneable {
    private String dataFrameName;
    private List<Registry> registryList;
    private String watchedValue;
    private DataSignature signature = new DataSignature();

    public DataFrame (String filepath) throws IOException, IndexOutOfBoundsException, CloneNotSupportedException {
        String[] filepathSplitBySlash = filepath.split("/");
        String lastBit = filepathSplitBySlash[filepathSplitBySlash.length - 1];
        String[] nameSplitByDots = lastBit.split(".");
        dataFrameName = nameSplitByDots[nameSplitByDots.length - 1];
        List<String> fileLines = Files.readAllLines(Paths.get(filepath));
        String[] firstLine = fileLines.get(0).split(",");
        String[] secondLine = fileLines.get(1).split(",");
        if (firstLine.length != secondLine.length)
            throw new IndexOutOfBoundsException("File signature does not match the data.");
        signature = new DataSignature();
        for (int i = 0; i < firstLine.length; i++)
        {
            if(NumericDataValue.wouldFit(secondLine[i]))
            {
                signature.add(new NumericDataValue(firstLine[i]));
                continue;
            }
            if(TextDataValue.wouldFit(secondLine[i]))
            {
                signature.add(new TextDataValue(firstLine[i]));
                continue;
            }
        }
        Registry baseRegistry = new Registry(signature);
        for (int i = 1; i < fileLines.size(); i++)
        {
            Registry newRegistry = baseRegistry.clone();
            String[] lineBits = fileLines.get(i).split(",");
            for (int j = 0; j < lineBits.length; j++)
            {
                newRegistry.setValue(((DataValue)signature.get(j)).getName(), lineBits[j]);
            }

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
        return false;
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
}
