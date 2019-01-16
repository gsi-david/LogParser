package com.gsi;

import com.gsi.exceptions.ArgumentException;
import com.gsi.utils.LogParser;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            // Get an instance of LogParser and read the log file
            LogParser.init(args);
        } catch (ArgumentException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("Exception open file");
        }

    }
}
