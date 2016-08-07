package org.ng.simplestock.stock;

import org.junit.Before;
import org.junit.Test;
import org.ng.simplestock.domain.BigDecimalUtil;
import org.ng.simplestock.domain.CommonStock;
import org.ng.simplestock.domain.Stock;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

/**
 * Tests for StockStore.
 */
public class StockStoreImplTest {
    private StockStore stockStore;

    @Before
    public void setup() {
        stockStore = new StockStoreImpl();
    }

    @Test
    public void storeStock_appliesStockToStore() {
        CommonStock commonStock = new CommonStock("TST", BigDecimalUtil.build("0.01"), BigDecimalUtil.build("10"));
        stockStore.storeStock(commonStock);

        Stock retrievedStock = stockStore.retrieveStock("TST");
        assertThat(retrievedStock.getSymbol(), is("TST"));
        assertThat(retrievedStock.getLastDividend(), is(BigDecimalUtil.build("10")));
        assertThat(retrievedStock.getParValue(), is(BigDecimalUtil.build("0.01")));
    }

    @Test
    public void storeStock_replacesExistingStockInStore() {
        CommonStock commonStock = new CommonStock("TST", BigDecimalUtil.build("0.01"), BigDecimalUtil.build("10"));
        stockStore.storeStock(commonStock);

        CommonStock commonStockUpdate = new CommonStock("TST", BigDecimalUtil.build("0.02"), BigDecimalUtil.build("20"));
        stockStore.storeStock(commonStockUpdate);

        Stock retrievedStock = stockStore.retrieveStock("TST");
        assertThat(retrievedStock.getSymbol(), is("TST"));
        assertThat(retrievedStock.getLastDividend(), is(BigDecimalUtil.build("20")));
        assertThat(retrievedStock.getParValue(), is(BigDecimalUtil.build("0.02")));
    }

    @Test
    public void retrieveStock_returnsNullIfNoMatchingStockFound() {
        CommonStock commonStock = new CommonStock("TST", BigDecimalUtil.build("0.01"), BigDecimalUtil.build("10"));
        stockStore.storeStock(commonStock);

        assertThat(stockStore.retrieveStock("UNK"), nullValue());
    }

    @Test
    public void loadStocks_loadsExpectedData() {
        stockStore.loadStocks();

        Stock stock = stockStore.retrieveStock("TEA");
        assertThat(stock.getSymbol(), is("TEA"));

        stock = stockStore.retrieveStock("POP");
        assertThat(stock.getSymbol(), is("POP"));

        stock = stockStore.retrieveStock("GIN");
        assertThat(stock.getSymbol(), is("GIN"));

        stock = stockStore.retrieveStock("ALE");
        assertThat(stock.getSymbol(), is("ALE"));

        stock = stockStore.retrieveStock("JOE");
        assertThat(stock.getSymbol(), is("JOE"));
    }
}