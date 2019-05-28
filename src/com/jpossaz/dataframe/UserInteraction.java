package com.jpossaz.dataframe;

import com.jpossaz.dataframe.datavalues.DataValue;
import com.jpossaz.dataframe.datavalues.NumericDataValue;
import com.jpossaz.dataframe.datavalues.TextDataValue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The class that implements the static methods for user interaction.
 */
public class UserInteraction {
    /**
     * Whether or not the application is running.
     */
    private static boolean running = false;

    /**
     * The DataFrame that is currently selected.
     */
    private static DataFrame selectedDataFrame;

    /**
     * The value currently watched.
     */
    private static String watchedValue = "";

    /**
     * The list of loaded DataFrames.
     */
    private static List<DataFrame> loadedDataFrames = new ArrayList<>();

    /**
     * The scanner that reads from the command line.
     */
    private static Scanner scanner;

    /**
     * Initialize the REPL (Read, eval, print, loop)
     */
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

    /**
     * Verify if a name is valid to be loaded into the list.
     * @param name The name of the DataFrame to be verified.
     * @return True if the name would not generate any conflict. False otherwise.
     */
    private static boolean verifyFrameName(String name) {
        for (DataFrame currentFrame : loadedDataFrames) {
            if (name.equals(currentFrame.getDataFrameName())) return false;
        }
        return true;
    }

    /**
     * The main read, eval, print, loop of the application.
     */
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
            if (commandBits[0].equals("help")) {
                System.out.println("load [archivo]: Permite cargar un DataFrame desde un archivo .csv.\n" +
                        "unload [nombre]: Permite eliminar un DataFrame de la lista de dataframes cargados.\n" +
                        "save [nombre]: Permite guardar un DataFrame a un archivo .csv.\n" +
                        "filter [operación]: Permite obtener un DataFrame que es la versión filtrada del dataframe seleccionado actualmente, agregándolo a la lista de dataframes cargados.\n" +
                        "calculate [operación]: Permite obtener el resultado de la operación estadística especificada, ejecutada sobre el dataframe.\n" +
                        "list: Imprime la lista de dataframes cargados a los que el usuario puede acceder.\n" +
                        "columns: Imprime la lista de columnas que componen el dataframe seleccionado. \n" +
                        "select [nombre]: Selecciona un DataFrame de la lista de dataframes a los que el usuario puede acceder. \n" +
                        "watch [columna]: Selecciona la columna a observar dentro del dataframe seleccionado.\n");
            }
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
                System.out.format("%25s%14s%14s\n", "NAME", "COLUMNS", "SIZE");
                for (int i = 0; i < loadedDataFrames.size(); i++)
                {
                    DataFrame thisFrame = loadedDataFrames.get(i);
                    //System.out.println(thisFrame.getDataFrameName() + "\t" + thisFrame.getColumnCount() + "\t" +
                            //thisFrame.size());
                    System.out.format("%25s%14d%14d\n", thisFrame.getDataFrameName(), thisFrame.getColumnCount(), thisFrame.size());
                }
                return;
            }
            if (commandBits.length > 1)
            {
                if (commandBits.length > 2)
                {
                    if (commandBits[0].equals("filter")) {
                        Comparable filterItem = null;
                        if(NumericDataValue.wouldFit(commandBits[2]))
                        {
                            filterItem = Double.parseDouble(commandBits[2]);
                        }
                        if(filterItem == null && TextDataValue.wouldFit(commandBits[2]))
                        {
                            filterItem = commandBits[2];
                        }
                        if (filterItem == null)
                        {
                            System.out.println("Unable to build a comparable object out of the argument passed.");
                        }
                        DataFrame result = null;
                        if (commandBits[1].equals("!=")) {
                            result = DataFilter.filterNotEqualTo(selectedDataFrame, filterItem);
                        }
                        if (commandBits[1].equals("==")) {
                            result = DataFilter.filterEqualTo(selectedDataFrame, filterItem);
                        }
                        if (commandBits[1].equals("<")) {
                            result = DataFilter.filterLessThan(selectedDataFrame, filterItem);
                        }
                        if (commandBits[1].equals(">")) {
                            result = DataFilter.filterGreaterThan(selectedDataFrame, filterItem);
                        }
                        if (commandBits[1].equals("<=")) {
                            result = DataFilter.filterLessThanOrEqualTo(selectedDataFrame, filterItem);
                        }
                        if (commandBits[1].equals(">=")) {
                            result = DataFilter.filterGreaterThanOrEqualTo(selectedDataFrame, filterItem);
                        }
                        if (result == null)
                        {
                            System.out.println("Unable to run the filter operation.");
                            return;
                        }
                        String newName = result.getDataFrameName();
                        if(newName.contains("-Filter"))
                        {
                            String[] nameBits = newName.split("-Filter");
                            newName = "";
                            for(int i = 0; i < nameBits.length - 1; i++)
                            {
                                newName += nameBits[i];
                            }
                            int number = Integer.parseInt(nameBits[nameBits.length -1]);
                            number ++;
                            newName += "-Filter" + number;
                        }
                        else
                        {
                            newName += "-Filter1";
                        }
                        result.setDataFrameName(newName);
                        loadedDataFrames.add(result);
                        selectedDataFrame = result;
                    }
                }
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
