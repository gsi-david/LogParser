package com.gsi;

import com.gsi.utils.ArgsValidator;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ChangeFormatTest {

    @Test
    public void shouldChangeFormat() {
        assertThat(ArgsValidator.Arguments.ID.formatValue("09877359-D")).isEqualTo("09877359D");
    }

    @Test
    public void shouldDoNotChangeFormat() {
        assertThat(ArgsValidator.Arguments.ID.formatValue("09877359D")).isEqualTo("09877359D");
    }

    @Test
    public void shouldChangeFormatAndComparisonNotEquals() {
        assertThat(ArgsValidator.Arguments.ID.formatValue("09877359-D")).isNotEqualTo("09877359-D");
    }
}
