package ro.veltanmarius.mkcustomer.rest.controller;

import java.time.ZonedDateTime;

import lombok.Value;
import org.springframework.http.HttpStatus;

/**
 * @author Marius Veltan
 */
@Value
public class GlobalHttpErrorInfo {
    private ZonedDateTime timestamp;
    private String path;
    private HttpStatus httpStatus;
    private String message;

    public GlobalHttpErrorInfo(HttpStatus httpStatus, String path, String message) {
        this.timestamp = ZonedDateTime.now();
        this.httpStatus = httpStatus;
        this.path = path;
        this.message = message;
    }
}