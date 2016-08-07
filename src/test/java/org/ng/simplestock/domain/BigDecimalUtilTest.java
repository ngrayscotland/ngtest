package org.ng.simplestock.domain;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Tests for {@link BigDecimalUtil}
 */
public class BigDecimalUtilTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void calculateNthRoot_throwsIllegalArgumentExceptionWhenNegativeNumberSupplied() {
        expectedException.expect(IllegalArgumentException.class);

        BigDecimalUtil.calculateNthRoot(5, new BigDecimal(-25));
    }

    @Test
    public void calculateNthRoot_returnsZeroForZeroValue() {
        assertThat(BigDecimalUtil.calculateNthRoot(5, BigDecimal.ZERO), is(BigDecimal.ZERO));
    }

    @Test
    public void calculateNthRoot_calculatesBaseRootCorrectly() {
        assertThat(BigDecimalUtil.calculateNthRoot(1, new BigDecimal(25)),
                is(BigDecimalUtil.build("25")));
    }

    @Test
    public void calculateNthRoot_calculatesSquareRootCorrectly() {
        assertThat(BigDecimalUtil.calculateNthRoot(2, new BigDecimal(25)),
                is(BigDecimalUtil.build("5")));
    }

    @Test
    public void calculateNthRoot_calculatesCubeRootCorrectly() {
        assertThat(BigDecimalUtil.calculateNthRoot(3, new BigDecimal(512)),
                is(BigDecimalUtil.build("8")));
    }

    @Test
    public void calculateNthRoot_calculates5thRootCorrectly() {
        assertThat(BigDecimalUtil.calculateNthRoot(5, new BigDecimal(3125)),
                is(BigDecimalUtil.build("5")));
    }

    @Test
    public void calculateNthRoot_calculates5thRootCorrectlyForNonExactValue() {
        assertThat(BigDecimalUtil.calculateNthRoot(5, new BigDecimal(67)),
                is(BigDecimalUtil.build("2.32")));
    }
}