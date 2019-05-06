package com.jpossaz.dataframe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
                        loadedDataFrames.add(loadedFrame);
                        selectedDataFrame = loadedFrame;
                        System.out.println("Dataframe successfully loaded");
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
            }

        }
    }
}
