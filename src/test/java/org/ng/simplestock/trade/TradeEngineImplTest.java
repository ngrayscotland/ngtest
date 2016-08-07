package org.ng.simplestock.trade;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.ng.simplestock.domain.*;
import org.ng.simplestock.exception.NoMatchingTradesException;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link TradeEngineImpl}
 */
public class TradeEngineImplTest {

    private TradeEngineImpl tradeEngine = new TradeEngineImpl();

    private Stock stock;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setup() {
        tradeEngine = new TradeEngineImpl();
        stock = new CommonStock("TST", BigDecimalUtil.build("2"), BigDecimalUtil.build("0.5"));
    }

    @Test
    public void registerTrade_storesTradeWhenNonePresentForStock() {
        Trade trade = new Trade(stock, DateTime.now(), 10, TradeType.BUY, BigDecimalUtil.build("5"));
        tradeEngine.registerTrade(trade);

        List<Trade> tradeList = tradeEngine.retrieveTradeList(stock);

        assertThat(tradeList.size(), is(1));
        Trade tradeFromList = tradeList.get(0);
        assertThat(tradeFromList.getStock().getSymbol(), is(trade.getStock().getSymbol()));
        assertThat(tradeFromList.getQuantity(), is(10));
        assertThat(tradeFromList.getTradeType(), is(TradeType.BUY));
        assertThat(tradeFromList.getPrice(), is(BigDecimalUtil.build("5")));
        assertThat(tradeFromList.getTimestamp(), is(trade.getTimestamp()));
    }

    @Test
    public void registerTrade_storesTradeWhenOtherTradesForStockArePresent() {
        Trade trade = new Trade(stock, DateTime.now(), 10, TradeType.BUY, BigDecimalUtil.build("5"));
        tradeEngine.registerTrade(trade);

        Trade trade2 = new Trade(stock, DateTime.now(), 20, TradeType.BUY, BigDecimalUtil.build("5"));
        tradeEngine.registerTrade(trade2);

        List<Trade> tradeList = tradeEngine.retrieveTradeList(stock);

        assertThat(tradeList.size(), is(2));
        assertThat(tradeList.get(0).getQuantity(), is(10));
        assertThat(tradeList.get(1).getQuantity(), is(20));
    }

    @Test
    public void calculateWeightedStockPrice_throwsNoMatchingTradesExceptionWhenNoTradesArePresentForTheStock() {
        Trade trade = new Trade(stock, DateTime.now(), 10, TradeType.BUY, BigDecimalUtil.build("5"));
        tradeEngine.registerTrade(trade);
        expectedException.expect(NoMatchingTradesException.class);

        Stock testStock = new CommonStock("TS2", BigDecimal.ONE, BigDecimal.TEN);
        tradeEngine.calculateWeightedStockPrice(testStock, DateTime.now(), Duration.standardMinutes(1));
    }

    @Test
    public void calculateWeightedStockPrice_throwsNoMatchingTradesExceptionWhenNoTradesArePresentForTheSpecifiedTimePeriod() {
        Trade trade = new Trade(stock, DateTime.now().minus(Duration.standardMinutes(5)), 10, TradeType.BUY, BigDecimalUtil.build("5"));
        tradeEngine.registerTrade(trade);
        expectedException.expect(NoMatchingTradesException.class);

        tradeEngine.calculateWeightedStockPrice(stock, DateTime.now(), Duration.standardMinutes(1));
    }

    @Test
    public void calculateWeightedStockPrice_whenAllTradesMatch() {
        for (int i=1; i <=5; i++) {
            tradeEngine.registerTrade(new Trade(stock, DateTime.now().minus(Duration.standardSeconds(10)), 10, TradeType.BUY, BigDecimalUtil.build("5")));
        }

        BigDecimal weightedStockPrice = tradeEngine.calculateWeightedStockPrice(stock, DateTime.now(), Duration.standardMinutes(1));
        assertThat(weightedStockPrice, is(BigDecimalUtil.build("5")));
    }

    @Test
    public void calculateWeightedStockPrice_whenSomeTradesMatch() {
        for (int duration=1; duration < 5; duration++) {
            for (int i = 1; i <= 10; i++) {
                tradeEngine.registerTrade(new Trade(stock, DateTime.now().minus(Duration.standardMinutes(duration)), 10, TradeType.BUY, BigDecimalUtil.build("5")));
            }
        }

        for (int i=1; i <=10; i++) {
            tradeEngine.registerTrade(new Trade(stock, DateTime.now().minus(Duration.standardMinutes(5)), 10, TradeType.BUY, BigDecimalUtil.build("15")));
        }

        BigDecimal weightedStockPrice = tradeEngine.calculateWeightedStockPrice(stock, DateTime.now(), Duration.standardMinutes(5));
        assertThat(weightedStockPrice, is(BigDecimalUtil.build("5")));
    }

    @Test
    public void calculateAllShareIndex_whenSingleStockPresent() {
        for (int i=1; i <=5; i++) {
            tradeEngine.registerTrade(new Trade(stock, DateTime.now().minus(Duration.standardSeconds(10)), 10, TradeType.BUY, BigDecimalUtil.build("5")));
        }

        BigDecimal allShareIndex = tradeEngine.calculateAllShareIndex(DateTime.now(), Duration.standardMinutes(1));
        assertThat(allShareIndex, is(BigDecimalUtil.build("5")));
    }

    @Test
    public void calculateAllShareIndex_whenMultipleStocksArePresent() {
        for (int i=1; i <=5; i++) {
            tradeEngine.registerTrade(new Trade(stock, DateTime.now().minus(Duration.standardSeconds(10)), 10, TradeType.BUY, BigDecimalUtil.build("5")));
        }

        Stock otherStock = new CommonStock("TS2", BigDecimalUtil.build("2"), BigDecimalUtil.build("0.5"));
        for (int i=1; i <=5; i++) {
            tradeEngine.registerTrade(new Trade(otherStock, DateTime.now().minus(Duration.standardSeconds(10)), 10, TradeType.BUY, BigDecimalUtil.build("10")));
        }

        BigDecimal allShareIndex = tradeEngine.calculateAllShareIndex(DateTime.now(), Duration.standardMinutes(1));
        assertThat(allShareIndex, is(BigDecimalUtil.build("7.07")));
    }

    @Test
    public void calculateAllShareIndex_zeroWhenNoTradesArePresent() {
        assertThat(tradeEngine.calculateAllShareIndex(DateTime.now(), Duration.standardMinutes(1)), is(BigDecimal.ZERO));
    }
}