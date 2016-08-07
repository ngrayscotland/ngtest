package org.ng.simplestock.domain;

import java.math.BigDecimal;

/**
 * Common stock entry.
 */
public class CommonStock extends Stock {

    /**
     * Create a common stock entry.
     *
     * @param symbol   for the stock.
     * @param parValue par value.
     */
    public CommonStock(String symbol, BigDecimal parValue, BigDecimal lastDividend) {
        super(symbol, parValue, lastDividend);
    }
}
