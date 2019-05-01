package com.jpossaz.dataframe;

import com.jpossaz.dataframe.datavalues.DataValue;

import java.util.*;

public class DataSignature implements List<DataValue> {
    private List<DataValue> signature = new ArrayList<DataValue>();


    @Override
    public int size() {
        return signature.size();
    }

    @Override
    public boolean isEmpty() {
        return signature.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return signature.contains(o);
    }

    @Override
    public Iterator iterator() {
        return signature.iterator();
    }

    @Override
    public Object[] toArray() {
        return signature.toArray();
    }

    @Override
    public boolean add(DataValue dataValue) {
        return signature.add(dataValue);
    }

    @Override
    public boolean remove(Object o) {
        return signature.remove(o);
    }

    @Override
    public boolean addAll(Collection collection) {
        return signature.addAll(collection);
    }

    @Override
    public boolean addAll(int i, Collection collection) {
        return signature.addAll(i, collection);
    }

    @Override
    public void clear() {
        signature.clear();
    }

    @Override
    public DataValue get(int i) {
        return signature.get(i);
    }

    @Override
    public DataValue set(int i, DataValue dataValue) {
        return signature.set(i, dataValue);
    }

    @Override
    public void add(int i, DataValue dataValue) {
        signature.add(i, dataValue);
    }

    @Override
    public DataValue remove(int i) {
        return signature.remove(i);
    }

    @Override
    public int indexOf(Object o) {
        return signature.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return signature.lastIndexOf(o);
    }

    @Override
    public ListIterator listIterator() {
        return signature.listIterator();
    }

    @Override
    public ListIterator listIterator(int i) {
        return signature.listIterator();
    }

    @Override
    public List subList(int i, int i1) {
        return signature.subList(i, i1);
    }

    @Override
    public boolean retainAll(Collection collection) {
        return signature.removeAll(collection);
    }

    @Override
    public boolean removeAll(Collection collection) {
        return signature.removeAll(collection);
    }

    @Override
    public boolean containsAll(Collection collection) {
        return signature.containsAll(collection);
    }

    @Override
    public Object[] toArray(Object[] objects) {
        return signature.toArray();
    }
}
