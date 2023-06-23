package com.bs.perform.exceptions;
public class ResourceNotFoundException extends RuntimeException {
    private static final String NOT_FOUND_MESSAGE_FORMAT = "Performance not found for ID: %s";

    public ResourceNotFoundException(String message) {
        super(String.format(NOT_FOUND_MESSAGE_FORMAT, message));
    }
}