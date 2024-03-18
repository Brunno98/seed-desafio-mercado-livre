package br.com.brunno.mercadoLivre.shared;

import lombok.Getter;

@Getter
public class FieldError {

    private final String field;
    private final String message;

    public FieldError(org.springframework.validation.FieldError fieldError) {
        this.field = fieldError.getField();
        this.message = fieldError.getDefaultMessage();
    }
}
