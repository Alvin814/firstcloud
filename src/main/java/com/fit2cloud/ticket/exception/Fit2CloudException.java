package com.fit2cloud.ticket.exception;

public class Fit2CloudException extends Exception {
    private static final long serialVersionUID = -649559784594858788L;

    public Fit2CloudException() {
    }

    public Fit2CloudException(String message, Throwable cause) {
        super(message, cause);
    }

    public Fit2CloudException(String message) {
        super(message);
    }

    public Fit2CloudException(Throwable cause) {
        super(cause);
    }
}
