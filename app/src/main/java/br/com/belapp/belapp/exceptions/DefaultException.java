package br.com.belapp.belapp.exceptions;

/**
 * Created by Felipe Oliveira on 17/11/16.
 * flpdias14@gmail.com
 */
public class DefaultException extends Exception {

    protected String message;

    public DefaultException() {
        super("Erro");
        this.message = "Erro";
    }

    DefaultException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
