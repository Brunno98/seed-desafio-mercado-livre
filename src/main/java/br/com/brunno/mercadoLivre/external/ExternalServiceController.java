package br.com.brunno.mercadoLivre.external;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExternalServiceController {

    @PostMapping("/nota-fiscal")
    public String notaFiscal(@RequestBody @Valid NotaFiscalRequest notaFiscalRequest) {
        System.out.println("Gerando nota fiscal da compra "+notaFiscalRequest.getIdCompra() + " " +
                "realizada pelo usuario "+notaFiscalRequest.getIdUsuario());
        return "Nota fiscal gerada!";
    }

    @PostMapping("/vendedor/ranking")
    public String rankingVendedores(@RequestBody @Valid RankingVendendoresRequest rankingVendendoresRequest) {
        System.out.println("Ranking do vendedor "+rankingVendendoresRequest.getIdVendedor()+" recebido para " +
                "a compra "+rankingVendendoresRequest.getIdVenda());

        return "ranking do vendedor adicionado";
    }
}
