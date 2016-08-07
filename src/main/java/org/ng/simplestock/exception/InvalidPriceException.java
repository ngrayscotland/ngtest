package org.ng.simplestock.exception;

/**
 * Thrown when an invalid price is supplied to a function.
 */
public class InvalidPriceException extends RuntimeException {
    /**
     * Create an invalid price exception.
     *
     * @param message description of the error.
     */
    public InvalidPriceException(String message) {
        super(message);
    }
}
