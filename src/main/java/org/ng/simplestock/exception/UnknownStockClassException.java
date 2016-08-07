package org.ng.simplestock.exception;

/**
 * Thrown when an unknown stock class is encountered.
 */
public class UnknownStockClassException extends RuntimeException {

    /**
     * Create a UnknownStockClassException.
     *
     * @param message describing the exception.
     */
    public UnknownStockClassException(String message) {
        super(message);
    }
}
