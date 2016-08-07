package org.ng.simplestock.domain;

import java.math.BigDecimal;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Utility functions for BigDecimal objects.
 */
public class BigDecimalUtil {
    public static final int ROUNDING_MODE = BigDecimal.ROUND_HALF_UP;

    private static final int DEFAULT_SCALE = 2;

    private static final int UTIL_CALC_SCALE = 10;

    /**
     * Build a BigDecimal that has its scale set the default value.
     * @param val string representation of BigDecimal.
     * @return BigDecimal set to the default scale.
     */
    public static BigDecimal build(String val) {
        return new BigDecimal(val).setScale(DEFAULT_SCALE, ROUNDING_MODE);
    }

    /**
     * Updates a BigDecimal to the default scale.
     * @param val object whose scale is to be updated.
     * @return BigDecimal set to the default scale.
     */
    public static BigDecimal updateScale(BigDecimal val) {
        return val.setScale(DEFAULT_SCALE, ROUNDING_MODE);
    }

    /**
     * Calculate the nth root of a number.
     *
     * @param n     root value to be calculated e.g. 5th root.
     * @param value whose root is required.
     * @return nth root of the value supplied.
     */
    public static BigDecimal calculateNthRoot(final int n, final BigDecimal value) {
        checkNotNull(value);

        final BigDecimal p = BigDecimal.valueOf(.1).movePointLeft(UTIL_CALC_SCALE);

        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Negative value is invalid for nth root calculation");
        }

        if (value.equals(BigDecimal.ZERO)) {
            return BigDecimal.ZERO;
        }

        BigDecimal xPrev = value;
        BigDecimal x = value.divide(new BigDecimal(n), UTIL_CALC_SCALE, ROUNDING_MODE);

        while (x.subtract(xPrev).abs().compareTo(p) > 0) {
            xPrev = x;
            x = BigDecimal.valueOf(n - 1.0)
                    .multiply(x)
                    .add(value.divide(x.pow(n - 1), UTIL_CALC_SCALE, ROUNDING_MODE))
                    .divide(new BigDecimal(n), UTIL_CALC_SCALE, ROUNDING_MODE);
        }

        return updateScale(x);
    }
}
