package com.aws.file_upload.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidFileTypeException.class)
    public ResponseEntity<String> handleInvalidFileType(InvalidFileTypeException ex) {
        return ResponseEntity
                .badRequest()
                .body("Error: " + ex.getMessage());
    }

}
