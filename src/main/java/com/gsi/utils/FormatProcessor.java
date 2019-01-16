package com.gsi.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Formats allowed to process lines
 */
public enum FormatProcessor {

    F1("^D\\s([^\\s][A-z\\s]+[^\\s]),([^\\s][A-z\\s]+[^\\s]),([\\d^\\s]{8}[A-Z])$") {
        @Override
        public Matcher getMatcher(String line) {
            return getPattern().matcher(line);
        }
    },
    F2("^D\\s([^\\s][A-z\\s]+[^\\s])\\s;\\s([^\\s][A-z\\s]+[^\\s])\\s;\\s([\\d]{8}[-][A-Z])$") {
        @Override
        public Matcher getMatcher(String line) {
            return getPattern().matcher(line);
        }
    };

    private Pattern pattern;
    private String regEx;

    FormatProcessor(String regEx) {
        this.regEx = regEx;
        this. pattern = Pattern.compile(this.getRegEx());
    }

    /**
     * Build Map with the filter arguments from line
     *
     * @param line Line to build
     * @return Map<ArgsValidator.Arguments,String>
     */
    public Map<ArgsValidator.Arguments, String> buildFromLine(String line) {
        Map<ArgsValidator.Arguments, String> stringMap = new HashMap<>();
        Matcher matcher = getMatcher(line);
        if (matcher.find()) {
            stringMap.put(ArgsValidator.Arguments.CITY, ArgsValidator.Arguments.CITY.formatValue(matcher.group(2)));
            stringMap.put(ArgsValidator.Arguments.ID, ArgsValidator.Arguments.ID.formatValue(matcher.group(3)));
        }
        return stringMap;
    }

    /**
     * Process line and get matcher
     *
     * @param line Line to get matcher
     * @return Matcher
     */
    public Matcher getMatcher(String line) {
        return pattern.matcher(line);
    }

    /**
     * Get Map with all the formats allowed
     *
     * @return Map<String, FormatProcessor>
     */
    public static Map<String, FormatProcessor> buildFormatsMap() {
        return Arrays.stream(values())
                .collect(Collectors.toMap(
                        Enum::name,
                        format -> format
                ));
    }

    public String getRegEx() {
        return regEx;
    }

    public Pattern getPattern() {
        return pattern;
    }
}
