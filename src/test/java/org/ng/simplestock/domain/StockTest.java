package org.ng.simplestock.domain;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Tests for {@link Stock}
 */
public class StockTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void constructor_throwsIllegalArgumentExceptionWhenParValueIsNegative() {
        expectedException.expect(IllegalArgumentException.class);

        new TestStock("TST", BigDecimalUtil.build("-1"), BigDecimal.ZERO);
    }

    @Test
    public void constructor_throwsIllegalArgumentExceptionWhenLastDividendIsNegative() {
        expectedException.expect(IllegalArgumentException.class);

        new TestStock("TST", BigDecimal.ZERO, BigDecimalUtil.build("-1"));
    }

    @Test
    public void constructor_buildsStock() {
        Stock stock = new TestStock("TST", BigDecimalUtil.build("1"), BigDecimalUtil.build("2"));

        assertThat(stock.getSymbol(), is("TST"));
        assertThat(stock.getParValue(), is(BigDecimalUtil.build("1")));
        assertThat(stock.getLastDividend(), is(BigDecimalUtil.build("2")));
    }

    private class TestStock extends Stock {
        private TestStock(String symbol, BigDecimal parValue, BigDecimal lastDividend) {
            super(symbol, parValue, lastDividend);
        }
    }
}