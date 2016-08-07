package org.ng.simplestock.trade;

import org.joda.time.DateTime;
import org.joda.time.ReadableDuration;
import org.ng.simplestock.domain.*;
import org.ng.simplestock.exception.NoMatchingTradesException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Implementation of the trade engine that stores trades in memory and provides functions across those trades.
 */
public class TradeEngineImpl implements TradeEngine {

    private final Map<String, List<Trade>> tradeMap = new HashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerTrade(Trade trade) {
        checkNotNull(trade);

        List<Trade> tradeList = tradeMap.get(trade.getStock().getSymbol());

        if (tradeList == null) {
            tradeList = new ArrayList<>();
            tradeList.add(trade);
            tradeMap.put(trade.getStock().getSymbol(), tradeList);
        } else {
            tradeList.add(trade);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Trade> retrieveTradeList(Stock stock) {
        checkNotNull(stock);

        List<Trade> tradeList = tradeMap.get(stock.getSymbol());

        if (tradeList == null) {
            throw new NoMatchingTradesException();
        }

        return tradeList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal calculateWeightedStockPrice(Stock stock, DateTime startTime, ReadableDuration durationBeforeStartTime) {
        checkNotNull(stock);
        return calculateWeightedStockPrice(stock.getSymbol(), startTime, durationBeforeStartTime);
    }

    private BigDecimal calculateWeightedStockPrice(String stockSymbol, DateTime startTime, ReadableDuration durationBeforeStartTime) {
        checkNotNull(stockSymbol);
        checkNotNull(startTime);
        checkNotNull(durationBeforeStartTime);

        BigDecimal weightedStockPrice;

        List<Trade> tradeList = tradeMap.get(stockSymbol);

        if (tradeList == null) {
            throw new NoMatchingTradesException();
        }

        DateTime prevStartTime = startTime.minus(durationBeforeStartTime);
        List<Trade> tradeMatchingTimePeriodList = tradeList.stream().filter(
                trade -> trade.getTimestamp().isBefore(startTime)
                        && trade.getTimestamp().isAfter(prevStartTime)).collect(Collectors.toList());

        if (tradeMatchingTimePeriodList.isEmpty()) {
            throw new NoMatchingTradesException();
        }

        BigDecimal sumOfTrades = BigDecimalUtil.build("0");
        int totalQuantity = 0;

        for (Trade trade : tradeMatchingTimePeriodList) {
            sumOfTrades = sumOfTrades.add(trade.getTotalValueOfTrade());
            totalQuantity += trade.getQuantity();
        }

        weightedStockPrice = sumOfTrades.divide(new BigDecimal(totalQuantity), BigDecimalUtil.ROUNDING_MODE);

        return weightedStockPrice;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal calculateAllShareIndex(DateTime startTime, ReadableDuration durationBeforeStartTime) {
        checkNotNull(startTime);
        checkNotNull(durationBeforeStartTime);

        BigDecimal cumulativeWeightedStockPrice = null;

        if (tradeMap.isEmpty()) {
            return BigDecimal.ZERO;
        }

        for (String key : tradeMap.keySet()) {
            if (cumulativeWeightedStockPrice == null) {
                cumulativeWeightedStockPrice = calculateWeightedStockPrice(key, startTime, durationBeforeStartTime);
            } else {
                cumulativeWeightedStockPrice = cumulativeWeightedStockPrice.multiply(calculateWeightedStockPrice(key, startTime, durationBeforeStartTime));
            }
        }

        return BigDecimalUtil.calculateNthRoot(tradeMap.keySet().size(), cumulativeWeightedStockPrice);
    }
}
