package com.jpossaz.dataframe;

import java.io.IOException;


/**
 * The main class of the application.
 * Used since the application could later be ported to a platform where it would be initialized in a different way.
 */
public class Main {
    /**
     * Entry point of the application.
     * @param args Command line parameters to be passed in.
     */
    public static void main(String [] args)
    {
        UserInteraction.replInit();
    }
}
