package br.com.brunno.mercadoLivre.shared;

import lombok.Getter;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class FieldErrorResponse {

    private final List<br.com.brunno.mercadoLivre.shared.FieldError> errors;

    public FieldErrorResponse(List<FieldError> fieldErrors) {
        errors = fieldErrors.stream()
                .map(br.com.brunno.mercadoLivre.shared.FieldError::new).collect(Collectors.toList());
    }
}
