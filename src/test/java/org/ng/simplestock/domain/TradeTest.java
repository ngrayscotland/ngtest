package org.ng.simplestock.domain;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Tests for {@link Trade}
 */
public class TradeTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private Stock stock;

    @Before
    public void setup() {
        stock = new CommonStock("TST", BigDecimalUtil.build("2"), BigDecimalUtil.build("0.5"));
    }

    @Test
    public void constructor_throwsIllegalArgumentExceptionWhenQuantityIsZero() {
        expectedException.expect(IllegalArgumentException.class);

        new Trade(stock, DateTime.now(), 0, TradeType.SELL, BigDecimalUtil.build("5"));
    }

    @Test
    public void constructor_throwsIllegalArgumentExceptionWhenQuantityIsNegative() {
        expectedException.expect(IllegalArgumentException.class);

        new Trade(stock, DateTime.now(), -1, TradeType.SELL, BigDecimalUtil.build("5"));
    }

    @Test
    public void constructor_throwsIllegalArgumentExceptionWhenPriceIsZero() {
        expectedException.expect(IllegalArgumentException.class);

        new Trade(stock, DateTime.now(), 1, TradeType.SELL, BigDecimal.ZERO);
    }

    @Test
    public void constructor_throwsIllegalArgumentExceptionWhenPriceIsNegative() {
        expectedException.expect(IllegalArgumentException.class);

        new Trade(stock, DateTime.now(), 1, TradeType.SELL, BigDecimalUtil.build("-5"));
    }

    @Test
    public void getTotalValueOfTrade_calculatesCorrectTotal() {
        Trade trade = new Trade(stock, DateTime.now(), 10, TradeType.BUY, BigDecimalUtil.build("5"));

        assertThat(trade.getTotalValueOfTrade(), is(BigDecimalUtil.build("50")));
    }
}