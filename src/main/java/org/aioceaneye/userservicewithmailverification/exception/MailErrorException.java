package org.aioceaneye.userservicewithmailverification.exception;

public class MailErrorException extends RuntimeException {

    public MailErrorException(String message) {
        super(message);
    }
}
