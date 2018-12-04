package br.com.belapp.belapp.exceptions;

/**
 * Created by Felipe Oliveira on 17/11/16.
 * flpdias14@gmail.com
 */
public class ValidationException extends DefaultException {

    public ValidationException(){
        super("Erro ao validar os campos!");
        this.message = "Erro ao validar os campos!";
    }

    public ValidationException(String message){
        super(message);
        this.message = message;
    }
}
