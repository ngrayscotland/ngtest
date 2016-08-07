package org.ng.simplestock.stock;

import org.ng.simplestock.domain.BigDecimalUtil;
import org.ng.simplestock.domain.CommonStock;
import org.ng.simplestock.domain.PreferredStock;
import org.ng.simplestock.domain.Stock;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple memory based stock store.
 */
public class StockStoreImpl implements StockStore {
    private final Map<String, Stock> stockMap = new HashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void storeStock(Stock stock) {
        stockMap.remove(stock.getSymbol());
        stockMap.put(stock.getSymbol(), stock);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stock retrieveStock(String stockSymbol) {
        return stockMap.get(stockSymbol);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadStocks() {
       // Load the sample data into the store.
        storeStock(new CommonStock("TEA", BigDecimalUtil.build("1"), BigDecimalUtil.build("0")));
        storeStock(new CommonStock("POP", BigDecimalUtil.build("1"), BigDecimalUtil.build("0.08")));
        storeStock(new CommonStock("ALE", BigDecimalUtil.build("0.60"), BigDecimalUtil.build("0.23")));
        storeStock(new PreferredStock("GIN", BigDecimalUtil.build("1"), BigDecimalUtil.build("0.08"), BigDecimalUtil.build("2")));
        storeStock(new CommonStock("JOE", BigDecimalUtil.build("2.5"), BigDecimalUtil.build("0.13")));
    }
}
