package com.jpossaz.dataframe;

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
    }
}
