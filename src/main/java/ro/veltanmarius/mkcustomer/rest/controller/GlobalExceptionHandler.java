package ro.veltanmarius.mkcustomer.rest.controller;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ro.veltanmarius.mkcustomer.exceptions.InvalidInputException;
import ro.veltanmarius.mkcustomer.exceptions.ObjectNotFoundException;

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

        return createHttpErrorInfo(NOT_FOUND, request, ex);
    }

    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ExceptionHandler(InvalidInputException.class)
    public @ResponseBody GlobalHttpErrorInfo handleInvalidInputException(
            ServerHttpRequest request, InvalidInputException ex) {

        return createHttpErrorInfo(UNPROCESSABLE_ENTITY, request, ex);
    }

    private GlobalHttpErrorInfo createHttpErrorInfo(
            HttpStatus httpStatus, ServerHttpRequest request, Exception ex) {

        final String path = request.getPath().pathWithinApplication().value();
        final String message = ex.getMessage();

        log.debug("Returning HTTP status: {} for path: {}, message: {}", httpStatus, path, message);
        return new GlobalHttpErrorInfo(httpStatus, path, message);
    }
}