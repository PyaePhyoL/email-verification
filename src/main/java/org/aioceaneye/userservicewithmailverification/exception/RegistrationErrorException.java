package org.aioceaneye.userservicewithmailverification.exception;

public class RegistrationErrorException extends RuntimeException {
    public RegistrationErrorException(String message) {
        super(message);
    }
}
