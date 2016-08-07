package org.ng.simplestock.trade;

import org.joda.time.DateTime;
import org.joda.time.ReadableDuration;
import org.ng.simplestock.domain.Stock;
import org.ng.simplestock.domain.Trade;

import java.math.BigDecimal;
import java.util.List;

/**
 * Interface to the trade engine.
 */
public interface TradeEngine {

    /**
     * Register a trade.
     *
     * @param trade to be registered.
     */
    void registerTrade(Trade trade);

    /**
     * Retrieve the list of trades for a particular stock.
     *
     * @param stock whose trades are required.
     * @throws org.ng.simplestock.exception.NoMatchingTradesException if no trades are found for the specified stock.
     */
    List<Trade> retrieveTradeList(Stock stock);

    /**
     * Calculate the weighted stock price for trades over a defined time period for a particular stock.
     *
     * @param stock                   whose weighted price is required.
     * @param startTime               start time that trades should have occurred before.
     * @param durationBeforeStartTime time period prior to the start time to find trades across.
     * @return weighted stock price for the specified stock and time period.
     * @throws org.ng.simplestock.exception.NoMatchingTradesException if no trades are found for the specified stock and time period.
     */
    BigDecimal calculateWeightedStockPrice(Stock stock, DateTime startTime, ReadableDuration durationBeforeStartTime);

    /**
     * Calculate the all share index for trades over the specified time period.
     *
     * @param startTime               start time that trades should have occurred before.
     * @param durationBeforeStartTime time period prior to the start time to find trades across.
     * @return all share index, calculated as the geometric mean of each stocks volume weighted stock price.
     */
    BigDecimal calculateAllShareIndex(DateTime startTime, ReadableDuration durationBeforeStartTime);
}
