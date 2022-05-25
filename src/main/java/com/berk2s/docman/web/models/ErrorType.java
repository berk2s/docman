package com.berk2s.docman.web.models;

import lombok.Getter;

/**
 * Error types
 * These types may be displayed value of error_type field in a error response
 */
@Getter
public enum ErrorType {
    INVALID_REQUEST("invalid_request"),
    REPORT_ERROR("report_error");

    private final String error;

    ErrorType(String e) {
        this.error = e;
    }

    @Override
    public String toString() {
        return error;
    }
}