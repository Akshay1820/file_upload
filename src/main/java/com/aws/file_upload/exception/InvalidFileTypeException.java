package com.aws.file_upload.exception;

public class InvalidFileTypeException extends RuntimeException{

    public InvalidFileTypeException(String message) {
        super(message);
    }
}
