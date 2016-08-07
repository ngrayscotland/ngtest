package org.ng.simplestock.domain;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Tests for {@link CommonStock}
 */
public class CommonStockTest {

    @Test
    public void constructor_buildsStock() {
        CommonStock stock = new CommonStock("TST", BigDecimalUtil.build("1"), BigDecimalUtil.build("2"));

        assertThat(stock.getSymbol(), is("TST"));
        assertThat(stock.getParValue(), is(BigDecimalUtil.build("1")));
        assertThat(stock.getLastDividend(), is(BigDecimalUtil.build("2")));
    }
}