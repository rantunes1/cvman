package com.glintt.cvm.exception;

public class ApplicationException extends Exception {

    private static final long serialVersionUID = 1284355845666332475L;

    public ApplicationException(Exception ex) {
        super(ex);
    }

    public ApplicationException(String message) {
        super(message);
    }
}
