package com.jpossaz.dataframe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserInteraction {
    private static boolean running = false;

    private static DataFrame selectedDataFrame;

    private static String watchedValue = "";

    private static List<DataFrame> loadedDataFrames = new ArrayList<>();

    private static Scanner scanner;

    public static void replInit ()
    {
        running = true;
        scanner = new Scanner(System.in);
        System.out.println("Data Frame REPL 1.0 by Juan Pablo Ossa Zapata");
        System.out.println("Type 'help' for a command list.");
        while (running)
        {
            repl();
        }
    }

    private static boolean verifyFrameName(String name) {
        for (DataFrame currentFrame : loadedDataFrames) {
            if (name.equals(currentFrame.getDataFrameName())) return false;
        }
        return true;
    }

    private static void repl () {
        String dataFrameName;
        if (selectedDataFrame == null) {
            dataFrameName = "NONE";
            watchedValue = "NONE";
        } else {
            dataFrameName = selectedDataFrame.getDataFrameName();
            watchedValue = selectedDataFrame.getWatchedValue();
        }
        System.out.print("[ " + dataFrameName + "," + watchedValue + " ]> ");
        String command = scanner.nextLine();
        String[] commandBits = Utils.splitCommandLine(command);
        if (commandBits.length > 0)
        {
            if (commandBits[0].equals("columns")) {
                if (selectedDataFrame == null) {
                    System.out.println("No dataframe is currently selected");
                } else {
                    System.out.println("Columns on the current dataframe:");
                    for (String s : selectedDataFrame.getColumnNames()) {
                        System.out.println(s);
                    }
                }
            }
            if (commandBits[0].equals("list"))
            {
                System.out.println("Listing all loaded dataframes...");
                //System.out.println("NAME\tCOLUMNS\tSIZE");
                System.out.format("%14s%14s%14s\n", "NAME", "COLUMNS", "SIZE");
                for (int i = 0; i < loadedDataFrames.size(); i++)
                {
                    DataFrame thisFrame = loadedDataFrames.get(i);
                    //System.out.println(thisFrame.getDataFrameName() + "\t" + thisFrame.getColumnCount() + "\t" +
                            //thisFrame.size());
                    System.out.format("%14s%14d%14d\n", thisFrame.getDataFrameName(), thisFrame.getColumnCount(), thisFrame.size());
                }
                return;
            }
            if (commandBits.length > 1)
            {
                if (commandBits[0].equals("load"))
                {
                    String filename = commandBits[1];
                    try {
                        System.out.println("Loading the file " + filename + "...");
                        DataFrame loadedFrame = new DataFrame(filename);
                        if (verifyFrameName(loadedFrame.getDataFrameName())) {
                            loadedDataFrames.add(loadedFrame);
                            selectedDataFrame = loadedFrame;
                            System.out.println("Dataframe successfully loaded");
                        } else {
                            System.out.println("Another loaded dataframe already exists with the same name");
                        }
                    } catch (CloneNotSupportedException e) {
                        System.out.println("Internal java exception.");
                    } catch (IOException e) {
                        System.out.println("Unable to open " + filename);
                    }
                    return;
                }
                if (commandBits[0].equals("unload"))
                {
                    String frameName = commandBits[1];
                    boolean unloaded = false;
                    for (DataFrame thisFrame : loadedDataFrames)
                    {
                        if (thisFrame.getDataFrameName().equals(frameName))
                        {
                            unloaded = true;
                            if (thisFrame.equals(selectedDataFrame))
                            {
                                if (loadedDataFrames.size() == 1)
                                {
                                    selectedDataFrame = null;
                                }
                                else
                                {
                                    selectedDataFrame = loadedDataFrames.get(0);
                                }
                            }
                            loadedDataFrames.remove(thisFrame);
                            System.out.println("Successfully unloaded the dataframe " + frameName);
                        }
                    }
                    if (!unloaded)
                    {
                        System.out.println("Unable to unload " + frameName);
                    }
                }
                if (commandBits[0].equals("save"))
                {
                    if (selectedDataFrame != null)
                    {
                        System.out.println("Trying to save dataframe...");
                        try {
                            String filename = commandBits[1].replace(".csv", "");
                            selectedDataFrame.saveDataFrame(filename);
                        }
                        catch (Exception e)
                        {
                            System.out.println("Unable to save dataframe as" + commandBits);
                        }
                    }
                }
                if (commandBits[0].equals("watch")) {
                    String columnToWatch = commandBits[1];
                    for (String s : selectedDataFrame.getColumnNames()) {
                        if (s.equals(columnToWatch)) {
                            selectedDataFrame.setWatchedValue(columnToWatch);
                            return;
                        }
                    }
                    System.out.println("Unable to watch the value " + columnToWatch);
                }
                if (commandBits[0].equals("calculate")) {
                    System.out.println("Attemping to run " + commandBits[1] + " on the dataframe");
                    if (commandBits[1].equals("trend")) {
                        System.out.println("Trend: " + DataOperation.obtainTrend(selectedDataFrame));
                        return;
                    }
                    if (commandBits[1].equals("deviation")) {
                        System.out.println("Deviation: " + DataOperation.obtainStandardDeviation(selectedDataFrame));
                        return;
                    }
                    if (commandBits[1].equals("mean")) {
                        System.out.println("Mean: " + DataOperation.obtainMean(selectedDataFrame));
                        return;
                    }
                    if (commandBits[1].equals("count")) {
                        System.out.println("Count: " + DataOperation.obtainCount(selectedDataFrame));
                        return;
                    }
                    if (commandBits[1].equals("max")) {
                        System.out.println("Maximum: " + DataOperation.obtainMaximum(selectedDataFrame));
                        return;
                    }
                    if (commandBits[1].equals("min")) {
                        System.out.println("Minimum: " + DataOperation.obtainMinimum(selectedDataFrame));
                        return;
                    }
                    System.out.println("Unable to run the specified operation.");
                }
            }
        }
    }
}
