package com.gsi.utils;

import com.gsi.exceptions.ArgumentException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Manager of read file and process lines with the filter validation
 */
public class LogParser {

    private Stream<String> lines;
    private ArgsValidator.Arguments argument;
    private String value;
    private Map<String, FormatProcessor> formatsMap;
    private FormatProcessor formatProcessor;

    private LogParser(Stream<String> lines, ArgsValidator.Arguments argument, String value) {
        this.lines = lines;
        this.argument = argument;
        this.value = value;
        this.formatsMap = FormatProcessor.buildFormatsMap();
        this.formatProcessor = FormatProcessor.F1;
    }

    /**
     * Get an instance of LogParser with the parameters
     *
     * @param argument Argument to filter
     * @param value    Value to filter
     * @return LogParser
     */
    private static LogParser from(Stream<String> lines, ArgsValidator.Arguments argument, String value) {
        return new LogParser(lines, argument, value);
    }

    /**
     * Validate arguments and execute read file
     *
     * @param args Arguments passed by the user
     * @throws IOException Throw exception if system can access to the file
     * @throws ArgumentException Throw argument exception when validate arguments passed by the user or file don't exits
     */
    public static void init(String[] args) throws IOException, ArgumentException {
        if(ArgsValidator.validateArguments(args)) {
            File file = Paths.get(args[0]).toFile();
            if (!file.exists())
                throw new ArgumentException("File not found");
            ArgsValidator.Arguments arguments = ArgsValidator.Arguments.valueOf(args[1]);
            LogParser.from(Files.lines(file.toPath()), arguments, arguments.formatValue(args[2])).read();
        }
    }

    /**
     * Read all the file lines
     */
    private void read() {
        lines.forEach(this::printLine);
    }

    /**
     * Gets if a lines is a format and change the actual format to process next lines
     *
     * @param line Line to validate
     * @return boolean
     */
    private boolean parseFormat(String line) {
        if (formatsMap.containsKey(line)) {
            this.formatProcessor = formatsMap.get(line);
            return true;
        }
        return false;
    }

    /**
     * Validate if the line matches with the parameters
     *
     * @param line Line to match
     * @return boolean
     */
    private boolean evaluateLine(String line) {
        if (!Objects.isNull(argument)) {
            return value.equals(formatProcessor.buildFromLine(line.trim()).get(argument));
        }
        return false;
    }

    /**
     * Print the line
     *
     * @param line Line to print
     */
    private void printLine(String line) {
        if (!line.isEmpty() && !parseFormat(line) && evaluateLine(line))
            System.out.println(formatOutputLine(line));
    }

    /**
     * Print a line with the
     *
     * @param line Line to print
     */
    private String formatOutputLine(String line) {
        if (!Objects.isNull(argument))
            return argument.buildOutputLine(line, formatProcessor);
        return "";
    }


}
