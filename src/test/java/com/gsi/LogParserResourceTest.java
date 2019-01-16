package com.gsi;

import com.gsi.exceptions.ArgumentException;
import com.gsi.utils.LogParser;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.URLDecoder;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class LogParserResourceTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private String filePath = "";

    @Before
    public void before() throws UnsupportedEncodingException {
        System.setOut(new PrintStream(outContent));

        filePath = URLDecoder.decode(new File(Objects.requireNonNull(getClass().getClassLoader().getResource("LogTest.txt")).getFile()).getAbsolutePath(), "UTF-8");
    }

    @Test
    public void shouldGetCitiesFromId() throws IOException, ArgumentException {
        String[] arguments = new String[3];
        arguments[0] = filePath;
        arguments[1] = "ID";
        arguments[2] = "54315871Z";
        LogParser.init(arguments);
        assertThat("SAN FRANCISCO" + "BARCELONA").isEqualToIgnoringWhitespace(outContent.toString());
    }

    @Test
    public void shouldLinesFromCity() throws IOException, ArgumentException {
        String[] arguments = new String[3];
        arguments[0] = filePath;
        arguments[1] = "CITY";
        arguments[2] = "SEVILLA";
        LogParser.init(arguments);
        assertThat("Neal Love,52498689Q")
                .isEqualToIgnoringWhitespace(outContent.toString());
    }

    @Test
    public void shouldGetEmptyLineFromCity() throws IOException, ArgumentException {
        String[] arguments = new String[3];
        arguments[0] = filePath;
        arguments[1] = "CITY";
        arguments[2] = "CUBA";
        LogParser.init(arguments);
        assertThat(outContent.toString()).isEmpty();
    }

    @Test(expected = ArgumentException.class)
    public void shouldGetIOExceptionWithInvalidFilePath() throws IOException, ArgumentException {
        String[] arguments = new String[3];
        arguments[0] = "FILE";
        arguments[1] = "CITY";
        arguments[2] = "CUBA";
        LogParser.init(arguments);
    }

}
