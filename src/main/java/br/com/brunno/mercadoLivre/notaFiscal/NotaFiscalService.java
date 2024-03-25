package br.com.brunno.mercadoLivre.notaFiscal;

import br.com.brunno.mercadoLivre.payment.Payment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class NotaFiscalService {

    public void process(Payment payment) {
        if (!payment.isSuccess()) return;

        ResponseEntity<String> notaFiscalResponse = new RestTemplate().postForEntity("http://localhost:8080/nota-fiscal", Map.of(
                        "idCompra", payment.getPurchase().getId(),
                        "idUsuario", payment.getPurchase().getBuyer().getId()),
                String.class);

        if (!notaFiscalResponse.getStatusCode().is2xxSuccessful()) {
            System.out.println("ERRO ao gerar nota fiscal!");
        }
    }
}
