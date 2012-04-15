package com.glintt.cvm.api;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.vaadin.appfoundation.persistence.data.AbstractPojo;


/**
 * Entity class for log messages.
 * 
 */
@Entity
public class LogMessage extends AbstractPojo {
    private static final long serialVersionUID = -5942942357329894740L;

    @Column(length = 3000)
    private String message;

    @Enumerated(EnumType.STRING)
    private LogLevel level;

    @Column(length = 1000)
    private String className;

    @Column(length = 500)
    private String methodName;

    private int lineNumber;

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LogLevel getLevel() {
        return this.level;
    }

    public void setLevel(LogLevel level) {
        this.level = level;
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public int getLineNumber() {
        return this.lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }
}
