package com.gsi.utils;

import com.gsi.exceptions.ArgumentException;

import java.util.Arrays;
import java.util.regex.Matcher;

/**
 * Util to validate the arguments
 */
public class ArgsValidator {

    /**
     * Validate the arguments passed by the user
     *
     * @param args Argument passed by user
     * @return boolean
     * @throws ArgumentException Exception with arguments
     */
    public static boolean validateArguments(String[] args) throws ArgumentException {
        if (args.length <= 0)
            throw new ArgumentException("File argument not found");
        if (args.length != 3)
            throw new ArgumentException("The numbers of arguments found are not allowed");
        else {
            if (Arguments.contains(args[1]))
                validate(Arguments.valueOf(args[1]), args[2]);
            else
                throw new ArgumentException("Argument \"" + args[1] + "\" not supported");
        }
        return true;
    }

    /**
     * Validate the argument passed by the user with the format supported
     *
     * @param argumentType Argument type to validate
     * @param argument     Argument to validate
     * @return String
     * @throws ArgumentException Exception with arguments
     */
    private static boolean validate(Arguments argumentType, String argument) throws ArgumentException {
        if (!argument.isEmpty() && argument.matches(argumentType.getFormat()))
            return true;
        else
            throw new ArgumentException(argumentType.name() + " not valid");
    }

    public enum Arguments {
        FILE(""),
        CITY("[A-z\\s]+") {
            @Override
            public String buildOutputLine(String line, FormatProcessor formatProcessor) {
                Matcher matcher = formatProcessor.getMatcher(line);
                if (matcher.find()) {
                    return super.buildOutputLine(matcher.group(1).trim()
                            .concat(",")
                            .concat(ID.formatValue(matcher.group(3).trim())), formatProcessor);
                }
                return super.buildOutputLine(line, formatProcessor);
            }
        },
        ID("\\d{8}(-)?[A-Z]{1}") {
            @Override
            public String buildOutputLine(String line, FormatProcessor formatProcessor) {
                Matcher matcher = formatProcessor.getMatcher(line);
                if (matcher.find()) {
                    return super.buildOutputLine(matcher.group(2), formatProcessor);
                }
                return super.buildOutputLine(line, formatProcessor);
            }

            @Override
            public String formatValue(String value) {
                return super.formatValue(value).replace("-", "");
            }
        };

        private String format;

        Arguments(String format) {
            this.format = format;
        }

        /**
         * Checks if string argument is present in the Arguments allowed
         *
         * @param value String argument type
         * @return boolean
         */
        public static boolean contains(String value) {
            return Arrays.stream(values())
                    .anyMatch(type -> type.name().equals(value));
        }

        /**
         * Build the output from process line
         *
         * @param line Line to process
         * @param formatProcessor Format to process line
         * @return String
         */
        public String buildOutputLine(String line, FormatProcessor formatProcessor) {
            return line.trim();
        }

        public String formatValue(String value) {
            return value.trim();
        }

        public String getFormat() {
            return format;
        }
    }
}
