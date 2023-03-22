package com.ksk.danal.exception;

public class FailedToRequestException extends RuntimeException {
    public FailedToRequestException(String msg) {super(msg);}
    public FailedToRequestException() {super();}
}
