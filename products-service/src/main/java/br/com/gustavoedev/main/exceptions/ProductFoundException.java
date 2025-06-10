package br.com.gustavoedev.main.exceptions;

public class ProductFoundException extends RuntimeException {

    public ProductFoundException() {
        super("Produto já existe");
    }

}
