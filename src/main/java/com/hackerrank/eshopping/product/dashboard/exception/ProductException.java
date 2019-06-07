package com.hackerrank.eshopping.product.dashboard.exception;

/**
 * Custom Exception to handle errors
 */
public class ProductException extends Exception {
    public ProductException() {
    }

    public ProductException(String message) {
        super(message);
    }
}
