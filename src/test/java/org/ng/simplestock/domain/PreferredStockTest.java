package org.ng.simplestock.domain;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link PreferredStock}
 */
public class PreferredStockTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void constructor_buildsStock() {
        PreferredStock stock = new PreferredStock("TST", BigDecimalUtil.build("1"), BigDecimalUtil.build("2"), BigDecimalUtil.build("3"));

        assertThat(stock.getSymbol(), is("TST"));
        assertThat(stock.getParValue(), is(BigDecimalUtil.build("1")));
        assertThat(stock.getLastDividend(), is(BigDecimalUtil.build("2")));
        assertThat(stock.getFixedDividend(), is(BigDecimalUtil.build("3")));
    }

    @Test
    public void constructor_throwsIllegalArgumentExceptionWhenFixedDividendIsNegative() {
        expectedException.expect(IllegalArgumentException.class);

        new PreferredStock("TST", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimalUtil.build("-1"));
    }
}