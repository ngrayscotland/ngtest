package org.ng.simplestock.stock;

import org.ng.simplestock.domain.*;
import org.ng.simplestock.exception.InvalidPriceException;
import org.ng.simplestock.exception.UnknownStockClassException;

import java.math.BigDecimal;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Calculator for stock information.
 */
public class StockCalculatorImpl implements StockCalculator {

    /**
     * {@inheritDoc}
     */
    public BigDecimal calculateDividendYield(Stock stock, BigDecimal stockPrice) {
        checkNotNull(stock);
        checkNotNull(stockPrice);

        BigDecimal yield;

        if (stockPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidPriceException("Price cannot be zero or negative");
        }

        if (stock instanceof CommonStock) {
            yield = stock.getLastDividend().divide(stockPrice, BigDecimalUtil.ROUNDING_MODE);
        } else if (stock instanceof PreferredStock) {
            PreferredStock preferredStock = (PreferredStock) stock;
            yield = BigDecimalUtil.updateScale(preferredStock.getParValue());
            yield = yield.multiply(preferredStock.getFixedDividend())
                    .divide(stockPrice, BigDecimalUtil.ROUNDING_MODE);
        } else {
            throw new UnknownStockClassException("unknown stock class: " + stock.getClass().getName());
        }

        return BigDecimalUtil.updateScale(yield);
    }

    /**
     * {@inheritDoc}
     */
    public BigDecimal calculatePriceEarningsRatio(Stock stock, BigDecimal stockPrice) {
        checkNotNull(stock);
        checkNotNull(stockPrice);

        if (stockPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidPriceException("Price cannot be zero or negative");
        }

        return stockPrice.divide(stock.getLastDividend(), BigDecimalUtil.ROUNDING_MODE);
    }
}
