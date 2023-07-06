package com.bs.perform.exceptions;
public class ResourceNotFoundException extends RuntimeException {
    private static final String NOT_FOUND_MESSAGE_FORMAT = "Resource not found for %s ID: %s";

    public ResourceNotFoundException(String entity, String id) {
        super(String.format(NOT_FOUND_MESSAGE_FORMAT, entity, id));
    }
}