package com.riteshbkadam.tms.exception;

public class InvalidStatusTransitionException extends RuntimeException {
    public InvalidStatusTransitionException(String msg) { super(msg); }
}
