package com.lx.springcloudproviderzookeeper.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.TimeoutException;

/**
 * @author lanxing
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public void HandleTimeOutException(TimeoutException timeoutException, Writer writer) throws IOException {
        writer.write(errorContent());
    }

    public String errorContent() {
        return "fault";
    }
}
