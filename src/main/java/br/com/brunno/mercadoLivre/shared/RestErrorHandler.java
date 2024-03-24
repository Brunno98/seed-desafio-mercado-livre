package br.com.brunno.mercadoLivre.shared;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestErrorHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ProblemDetail handle(BindException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "One or more validation errors occurred");
        problemDetail.setProperty("errors", e.getFieldErrors().stream().map(fieldError -> {
            String field = fieldError.getField();
            String defaultMessage = Optional.ofNullable(fieldError.getDefaultMessage()).orElse("invalid");
            return Map.of("field", field, "message", defaultMessage);
        }).collect(Collectors.toList()));
        return problemDetail;
    }
}
