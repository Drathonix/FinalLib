package com.vicious.finallib.common.nbt.reflect;

public class BadAnnotationException extends RuntimeException{
    public BadAnnotationException() {
    }

    public BadAnnotationException(String message) {
        super(message);
    }

    public BadAnnotationException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadAnnotationException(Throwable cause) {
        super(cause);
    }

    public BadAnnotationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
