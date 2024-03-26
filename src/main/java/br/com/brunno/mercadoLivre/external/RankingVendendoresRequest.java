package br.com.brunno.mercadoLivre.external;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RankingVendendoresRequest {

    @NotNull
    private String idVenda;

    @NotNull
    private Long idVendedor;

}
