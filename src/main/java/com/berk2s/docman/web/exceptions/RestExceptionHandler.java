package com.berk2s.docman.web.exceptions;

import com.berk2s.docman.web.models.ErrorDesc;
import com.berk2s.docman.web.models.ErrorResponseDto;
import com.berk2s.docman.web.models.ErrorType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.warn("HttpMessageNotReadableException: {}", ex.getMessage());
        return errorResponse(new ErrorResponseDto(ErrorType.INVALID_REQUEST, ex.getMessage(),  HttpStatus.BAD_REQUEST));
    }

    @Override
    protected ResponseEntity handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.warn("NoHandlerFoundException: {}", ex.getMessage());
        return errorResponse(new ErrorResponseDto(ErrorType.INVALID_REQUEST, ex.getMessage(),  HttpStatus.NOT_FOUND));
    }

    @Override
    protected ResponseEntity handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.warn("HttpMessageNotWritableException: {}", ex.getMessage());
        return errorResponse(new ErrorResponseDto(ErrorType.INVALID_REQUEST, ex.getMessage(),  HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Override
    protected ResponseEntity handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.warn("HttpMediaTypeNotAcceptableException: {}", ex.getMessage());
        return errorResponse(new ErrorResponseDto(ErrorType.INVALID_REQUEST, ex.getMessage(),  HttpStatus.BAD_REQUEST));
    }

    @Override
    protected ResponseEntity handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.warn("HttpMediaTypeNotSupportedException: {}", ex.getMessage());
        return errorResponse(new ErrorResponseDto(ErrorType.INVALID_REQUEST, ex.getMessage(),  HttpStatus.UNSUPPORTED_MEDIA_TYPE));
    }

    @Override
    protected ResponseEntity handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.warn("MissingServletRequestParameterException: {}", ex.getMessage());
        return errorResponse(new ErrorResponseDto(ErrorType.INVALID_REQUEST, ex.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @Override
    protected ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.warn("MethodArgumentNotValidException: {}", ex.getMessage());
        return errorResponse(new ErrorResponseDto(ErrorType.INVALID_REQUEST, ErrorDesc.BAD_REQUEST.getDesc(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<ErrorResponseDto> handleConstraintViolation(ConstraintViolationException ex) {
        log.warn("ConstraintViolationException: {}", ex.getMessage());
        return errorResponse(new ErrorResponseDto(ErrorType.INVALID_REQUEST, ex.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ErrorResponseDto> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.warn("IllegalArgumentException: {}", ex.getMessage());
        return errorResponse(new ErrorResponseDto(ErrorType.INVALID_REQUEST, ex.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(ReporterException.class)
    protected ResponseEntity<ErrorResponseDto> handleIllegalArgumentException(ReporterException ex) {
        log.warn("ReporterException: {}", ex.getMessage());
        return errorResponse(new ErrorResponseDto(ErrorType.REPORT_ERROR, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ResponseEntity<ErrorResponseDto> errorResponse(ErrorResponseDto errorResponseDto){
        return new ResponseEntity<>(errorResponseDto, errorResponseDto.getHttpStatus());
    }
}
