package org.ng.simplestock.domain;

import java.math.BigDecimal;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Preferred stock.
 */
public class PreferredStock extends Stock {
    private final BigDecimal fixedDividend;

    /**
     * Create a preferred stock entry.
     *
     * @param symbol        for the stock.
     * @param parValue      par value.
     * @param lastDividend  last dividend paid against the stock.
     * @param fixedDividend fixed dividend percentage for this preferred stock.
     */
    public PreferredStock(String symbol, BigDecimal parValue, BigDecimal lastDividend, BigDecimal fixedDividend) {
        super(symbol, parValue, lastDividend);

        this.fixedDividend = checkNotNull(fixedDividend);

        if (BigDecimal.ZERO.compareTo(fixedDividend) > 0) {
            throw new IllegalArgumentException("Fixed dividend cannot be negative");
        }
    }

    /**
     * @return fixed dividend value for this preferred stock.
     */
    public BigDecimal getFixedDividend() {
        return fixedDividend;
    }
}
