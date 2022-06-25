package Exceptions;

import jakarta.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class AlreadyHasException extends Exception{
    public AlreadyHasException() {}

    public AlreadyHasException(String message) {
        super(message);
    }
}
