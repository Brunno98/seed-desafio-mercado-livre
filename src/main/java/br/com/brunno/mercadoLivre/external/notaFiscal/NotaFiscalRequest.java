package br.com.brunno.mercadoLivre.external.notaFiscal;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class NotaFiscalRequest {

    @NotBlank
    private String idCompra;

    @NotBlank
    private String idUsuario;

}
