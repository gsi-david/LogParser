package com.gsi;

import com.gsi.exceptions.ArgumentException;
import com.gsi.utils.ArgsValidator;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ArgsValidatorTest {

    @Test(expected = ArgumentException.class)
    public void shouldTrowFileArgumentException() throws ArgumentException {
        ArgsValidator.validateArguments(new String[0]);
    }

    @Test(expected = ArgumentException.class)
    public void shouldTrowFileArgumentException2() throws ArgumentException {
        ArgsValidator.validateArguments(new String[]{"FILE", "CITY"});
    }

    @Test(expected = ArgumentException.class)
    public void shouldTrowFileArgumentException3() throws ArgumentException {
        ArgsValidator.validateArguments(new String[]{"FILE", "ASD"});
    }

    @Test
    public void shouldBuildMapFromArguments() throws ArgumentException {
        assertThat(ArgsValidator.validateArguments(new String[]{"FILE", "CITY", "BARCELONA"})).isTrue();
    }


}
