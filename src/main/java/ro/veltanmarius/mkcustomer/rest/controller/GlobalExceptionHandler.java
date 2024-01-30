package ro.veltanmarius.mkcustomer.rest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import ro.veltanmarius.mkcustomer.exceptions.InvalidInputException;
import ro.veltanmarius.mkcustomer.exceptions.ObjectNotFoundException;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

/**
 * @author Marius Veltan
 */
@Slf4j
@RestControllerAdvice
public final class GlobalExceptionHandler {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(ObjectNotFoundException.class)
    public @ResponseBody GlobalHttpErrorInfo handleNotFoundExceptions(
            ServerHttpRequest request, ObjectNotFoundException ex) {

        return createHttpErrorInfo(NOT_FOUND, request, ex, null);
    }

    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ExceptionHandler(InvalidInputException.class)
    public @ResponseBody GlobalHttpErrorInfo handleInvalidInputException(
            ServerHttpRequest request, InvalidInputException ex) {

        return createHttpErrorInfo(UNPROCESSABLE_ENTITY, request, ex, null);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(WebExchangeBindException.class)
    public @ResponseBody GlobalHttpErrorInfo handleBindErrors(
            ServerHttpRequest request, WebExchangeBindException ex) {
        log.debug("handleBindErrors start");
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(e -> "Field " + e.getField() + " - " + e.getDefaultMessage()).toList();
        return createHttpErrorInfo(BAD_REQUEST, request, ex, errors);
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public @ResponseBody GlobalHttpErrorInfo handleRuntimeExceptions(
            ServerHttpRequest request, RuntimeException ex) {
        log.debug("handleRuntimeExceptions start");
        return createHttpErrorInfo(INTERNAL_SERVER_ERROR, request, ex, null);
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public @ResponseBody GlobalHttpErrorInfo handleGeneralExceptions(
            ServerHttpRequest request, Exception ex) {
        log.debug("handleGeneralExceptions start");
        return createHttpErrorInfo(INTERNAL_SERVER_ERROR, request, ex, null);
    }

    private GlobalHttpErrorInfo createHttpErrorInfo(
            HttpStatus httpStatus, ServerHttpRequest request, Exception ex, List<String> errors) {

        final String path = request.getPath().pathWithinApplication().value();
        final String message;
        if (errors == null) {
            message = ex.getMessage();
        } else {
            message = String.join(" | ", errors);
        }

        log.debug("Returning HTTP status: {} for path: {}, message: {}", httpStatus, path, message);
        return new GlobalHttpErrorInfo(httpStatus, path, message);
    }
}