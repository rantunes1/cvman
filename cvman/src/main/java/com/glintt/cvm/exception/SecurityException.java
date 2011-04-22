package com.glintt.cvm.exception;


public class SecurityException extends ApplicationException {

    private static final long serialVersionUID = 1284355845666332475L;

    public SecurityException(Exception ex) {
        super(ex);
    }

    public SecurityException(String message) {
        super(message);
    }
}
