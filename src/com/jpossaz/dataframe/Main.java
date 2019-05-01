package com.jpossaz.dataframe;

import java.io.IOException;

public class Main {
    public static void main(String [] args)
    {
        final String dir = System.getProperty("user.dir");
        System.out.println("current dir = " + dir);
        try {
            DataFrame test = new DataFrame("1715906.csv");
            System.out.println(test);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}
