package com.kulushev.Orders.exception.notFound;

public class OrderNotFoundException extends RuntimeException{
    private static final String MESSAGE = "Order not found";

    public OrderNotFoundException() {
        super(MESSAGE);
    }

    public OrderNotFoundException(String message) {
        super(message);
    }
}
