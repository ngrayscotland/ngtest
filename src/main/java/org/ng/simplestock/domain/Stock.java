package org.ng.simplestock.domain;

import java.math.BigDecimal;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Base class for a stock entry in the simple exchange.
 */
public abstract class Stock {
    private final String symbol;
    private final BigDecimal parValue;
    private BigDecimal lastDividend;

    /**
     * Create a stock entry.
     *
     * @param symbol       for the stock.
     * @param parValue     par value.
     * @param lastDividend last dividend paid.
     */
    public Stock(String symbol, BigDecimal parValue, BigDecimal lastDividend) {
        this.symbol = checkNotNull(symbol);
        this.parValue = checkNotNull(parValue);
        if (BigDecimal.ZERO.compareTo(parValue) > 0) {
            throw new IllegalArgumentException("Par value cannot be negative");
        }

        this.lastDividend = checkNotNull(lastDividend);
        if (BigDecimal.ZERO.compareTo(lastDividend) > 0) {
            throw new IllegalArgumentException("Last dividend cannot be negative");
        }
    }

    /**
     * Update the last dividend value.
     *
     * @param lastDividend last dividend value.
     */
    public void setLastDividend(BigDecimal lastDividend) {
        this.lastDividend = lastDividend;
    }

    /**
     * @return symbol for the stock.
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * @return last dividend value.
     */
    public BigDecimal getLastDividend() {
        return lastDividend;
    }

    /**
     * @return par value.
     */
    public BigDecimal getParValue() {
        return parValue;
    }
}
