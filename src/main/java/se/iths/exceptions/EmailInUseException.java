package se.iths.exceptions;

public class EmailInUseException extends Exception
{
    public EmailInUseException(String errorMessage) {
        super(errorMessage);
    }
}
