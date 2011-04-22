package com.glintt.cvm.util;

import org.vaadin.appfoundation.persistence.facade.FacadeFactory;

import com.glintt.cvm.model.LogLevel;
import com.glintt.cvm.model.LogMessage;

/**
 * Logging utility class.
 */
public class Logging {

    // Define whether or not the log message should be printed to the console
    public static boolean LOG_TO_CONSOLE = true;

    private static void log(LogLevel logLevel, String msg) {
        log(logLevel, null, msg);
    }

    private static void log(LogLevel logLevel, Exception ex) {
        log(logLevel, ex, null);
    }

    private static void log(LogLevel logLevel, Exception ex, String msg) {
        if (LOG_TO_CONSOLE) {
            ex.printStackTrace();
        }

        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        LogMessage log = createLog(logLevel, (msg != null ? msg : ex.getMessage()), stackTraceElements);

        // Store the log message
        FacadeFactory.getFacade().store(log);

    }

    public static void debug(Exception e) {
        log(LogLevel.DEBUG, e);
    }

    public static void debug(String msg) {
        log(LogLevel.DEBUG, msg);
    }

    public static void info(Exception e) {
        log(LogLevel.INFO, e);
    }

    public static void info(String msg) {
        log(LogLevel.INFO, msg);
    }

    public static void warning(Exception e) {
        log(LogLevel.WARNING, e);
    }

    public static void warning(String msg) {
        log(LogLevel.WARNING, msg);
    }

    public static void error(Exception e) {
        log(LogLevel.ERROR, e);
    }

    public static void error(String msg) {
        log(LogLevel.ERROR, msg);
    }

    private static LogMessage createLog(LogLevel logLevel, String msg, StackTraceElement[] elements) {
        LogMessage log = new LogMessage();

        boolean next = false;
        for (StackTraceElement element : elements) {
            if (next) {
                log.setClassName(element.getClassName());
                log.setMethodName(element.getMethodName());
                log.setLineNumber(element.getLineNumber());
                break;
            }

            // If the element's class is this class, then we know that the next
            // element in the stack trace is the place form where the log
            // request originated.
            if (element.getClassName().equals(Logging.class.getName())) {
                next = true;
            }
        }

        log.setMessage(msg);
        log.setLevel(logLevel);

        return log;
    }

}
