package com.bs.perform.exceptions;

import org.springframework.web.client.ResourceAccessException;

public class ExternalServiceException extends RuntimeException {

    public ExternalServiceException(String message) {
        super(message);
    }
}
