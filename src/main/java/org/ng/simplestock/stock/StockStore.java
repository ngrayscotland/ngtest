package org.ng.simplestock.stock;

import org.ng.simplestock.domain.Stock;

/**
 * Interface to the store for storing and retrieving stocks.
 */
public interface StockStore {

    /**
     * Loads the stock data into the store.
     */
    void loadStocks();

    /**
     * Store stock, replacing any existing entry for the stock.
     * @param stock to be stored.
     */
    void storeStock(Stock stock);

    /**
     * Retrieve the stock that matches the supplied symbol.
     * @param stockSymbol stock symbol to match on.
     * @return matching stock or null if no match is found.
     */
    Stock retrieveStock(String stockSymbol);
}
