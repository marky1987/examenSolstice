package com.hackerrank.eshopping.product.dashboard.exception;

public class NotFoundDataException extends NullPointerException {
    public NotFoundDataException() {
    }

    public NotFoundDataException(String s) {
        super(s);
    }
}
