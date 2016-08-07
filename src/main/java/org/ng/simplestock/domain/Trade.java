package org.ng.simplestock.domain;

import org.joda.time.DateTime;

import java.math.BigDecimal;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A trade entry for a stock.
 */
public class Trade {
    private final Stock stock;
    private final DateTime timestamp;
    private final int quantity;
    private final TradeType tradeType;
    private final BigDecimal price;

    /**
     * Create a trade based on the supplied data.
     *
     * @param stock     associated stock for the trade.
     * @param timestamp time at which the trade occurred.
     * @param quantity  amount of shared traded.
     * @param tradeType type of trade e.g. buy or sell.
     * @param price     of the traded shares.
     */
    public Trade(Stock stock, DateTime timestamp, int quantity, TradeType tradeType, BigDecimal price) {
        this.stock = checkNotNull(stock);
        this.timestamp = checkNotNull(timestamp);
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        this.quantity = quantity;

        this.tradeType = checkNotNull(tradeType);
        this.price = BigDecimalUtil.updateScale(checkNotNull(price));

        if (BigDecimal.ZERO.compareTo(price) >= 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }
    }

    /**
     * @return stock associated with the trade.
     */
    public Stock getStock() {
        return stock;
    }

    /**
     * @return time and date the trade occurred.
     */
    public DateTime getTimestamp() {
        return timestamp;
    }

    /**
     * @return amount of shares traded.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @return type of trade e.g. buy or sell.
     */
    public TradeType getTradeType() {
        return tradeType;
    }

    /**
     * @return price of the share at the time of the trade.
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @return the total value of this trade i.e. price multiplied by quantity.
     */
    public BigDecimal getTotalValueOfTrade() {
        return price.multiply(new BigDecimal(quantity));
    }
}
