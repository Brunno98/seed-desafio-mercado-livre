package br.com.brunno.mercadoLivre.external.notaFiscal;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotaFiscalController {

    @PostMapping("/nota-fiscal")
    public String notaFiscal(@RequestBody @Valid NotaFiscalRequest notaFiscalRequest) {
        System.out.println("Gerando nota fiscal da compra "+notaFiscalRequest.getIdCompra() + " " +
                "realizada pelo usuario "+notaFiscalRequest.getIdUsuario());
        return "Nota fiscal gerada!";
    }
}
