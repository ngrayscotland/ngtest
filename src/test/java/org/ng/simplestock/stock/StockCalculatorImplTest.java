package org.ng.simplestock.stock;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.ng.simplestock.domain.BigDecimalUtil;
import org.ng.simplestock.domain.CommonStock;
import org.ng.simplestock.domain.PreferredStock;
import org.ng.simplestock.domain.Stock;
import org.ng.simplestock.exception.InvalidPriceException;
import org.ng.simplestock.exception.UnknownStockClassException;

import java.math.BigDecimal;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for the {@link StockCalculatorImpl}
 */
public class StockCalculatorImplTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private StockCalculatorImpl stockCalculator;

    @Before
    public void setup() {
        stockCalculator = new StockCalculatorImpl();
    }

    @Test
    public void calculateDividendYield_throwsUnknownStockClassExceptionWhenUnknownClassSupplied() {
        expectedException.expect(UnknownStockClassException.class);

        DummyStock dummyStock = new DummyStock("DUM", BigDecimalUtil.build("0.01"), BigDecimalUtil.build("1"));
        stockCalculator.calculateDividendYield(dummyStock, BigDecimal.TEN);
    }

    @Test
    public void calculateDividendYield_throwsInvalidPriceExceptionWhenPriceIsZero() {
        CommonStock commonStock = new CommonStock("TST", BigDecimalUtil.build("0.01"), BigDecimalUtil.build("10"));
        expectedException.expect(InvalidPriceException.class);

        stockCalculator.calculateDividendYield(commonStock, BigDecimal.ZERO);
    }

    @Test
    public void calculateDividendYield_throwsInvalidPriceExceptionWhenPriceIsNegative() {
        CommonStock commonStock = new CommonStock("TST", BigDecimalUtil.build("0.01"), BigDecimalUtil.build("10"));
        expectedException.expect(InvalidPriceException.class);

        stockCalculator.calculateDividendYield(commonStock, new BigDecimal(-1));
    }

    @Test
    public void calculateDividendYield_yieldIsCalculatedCorrectlyForCommonStock() {
        CommonStock commonStock = new CommonStock("TST", BigDecimalUtil.build("0.01"), BigDecimalUtil.build("10"));

        BigDecimal yield = stockCalculator.calculateDividendYield(commonStock, new BigDecimal(5));
        assertThat(yield, is(BigDecimalUtil.build("2")));
    }

    @Test
    public void calculateDividendYield_yieldIsCalculatedCorrectlyForPreferredStock() {
        PreferredStock preferredStock = new PreferredStock("TST", BigDecimalUtil.build("1"), BigDecimalUtil.build("10"), BigDecimalUtil.build("2"));

        BigDecimal yield = stockCalculator.calculateDividendYield(preferredStock, BigDecimalUtil.build("10"));
        assertThat(yield, is(BigDecimalUtil.build("0.20")));
    }

    @Test
    public void calculatePriceEarningsRatio_calculatesCorrectRatio() {
        PreferredStock preferredStock = new PreferredStock("TST", BigDecimalUtil.build("1"), BigDecimalUtil.build("10"), BigDecimalUtil.build("8"));

        BigDecimal priceEarningsRatio = stockCalculator.calculatePriceEarningsRatio(preferredStock, BigDecimalUtil.build("4"));
        assertThat(priceEarningsRatio, is(BigDecimalUtil.build("0.4")));
    }

    @Test
    public void calculatePriceEarningsRatio_throwsInvalidPriceExceptionWhenPriceIsZero() {
        PreferredStock preferredStock = new PreferredStock("TST", BigDecimalUtil.build("1"), BigDecimalUtil.build("10"), BigDecimalUtil.build("8"));
        expectedException.expect(InvalidPriceException.class);

        stockCalculator.calculatePriceEarningsRatio(preferredStock, BigDecimal.ZERO);
    }

    @Test
    public void calculatePriceEarningsRatio_throwsInvalidPriceExceptionWhenPriceIsNegative() {
        PreferredStock preferredStock = new PreferredStock("TST", BigDecimalUtil.build("1"), BigDecimalUtil.build("10"), BigDecimalUtil.build("8"));
        expectedException.expect(InvalidPriceException.class);

        stockCalculator.calculatePriceEarningsRatio(preferredStock, new BigDecimal(-1));
    }

    private class DummyStock extends Stock {
        DummyStock(String symbol, BigDecimal parValue, BigDecimal lastDividend) {
            super(symbol, parValue, lastDividend);
        }
    }
}