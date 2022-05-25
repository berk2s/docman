package com.berk2s.docman.web.exceptions;

/**
 * ReporterException could be thrown while generating a report
 */
public class ReporterException extends RuntimeException {
    public ReporterException(String message) {
        super(message);
    }
}
