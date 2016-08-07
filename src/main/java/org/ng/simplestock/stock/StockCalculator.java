package org.ng.simplestock.stock;

import org.ng.simplestock.domain.Stock;
import org.ng.simplestock.exception.UnknownStockClassException;

import java.math.BigDecimal;

/**
 * Interface to the stock stock.
 */
public interface StockCalculator {
    /**
     * Calculate the dividend yield for a given stock and price.
     *
     * @param stock      whose yield is to be calculated.
     * @param stockPrice price for the stock whose yield is to be calculated.
     * @return dividend yield for the stock.
     * @throws org.ng.simplestock.exception.InvalidPriceException if an invalid price is supplied as input.
     * @throws org.ng.simplestock.exception.UnknownStockClassException if a stock class is supplied that the method
     * does not support.
     */
    BigDecimal calculateDividendYield(Stock stock, BigDecimal stockPrice);

    /**
     * Calculate the price to earnings ratio for a given stock and price.
     *
     * @param stock      whose PE ratio is be calculated.
     * @param stockPrice price for the stock whose PE ratio is to be calculated.
     * @return price to earnings (PE) ratio.
     */
    BigDecimal calculatePriceEarningsRatio(Stock stock, BigDecimal stockPrice);
}
