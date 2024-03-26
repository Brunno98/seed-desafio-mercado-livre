package br.com.brunno.mercadoLivre.payment.notafiscal;

import br.com.brunno.mercadoLivre.payment.Payment;
import br.com.brunno.mercadoLivre.payment.SuccessPaymentListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/* contagem carga intrinseca:
    - SuccessPaymentListener
    - Payment

    total: 2
 */

@Service
public class NotaFiscalService implements SuccessPaymentListener {

    @Override
    public void process(Payment payment) {
        Assert.isTrue(payment.isSuccess(), "payment should be success");

        ResponseEntity<String> notaFiscalResponse = new RestTemplate().postForEntity("http://localhost:8080/nota-fiscal", Map.of(
                        "idCompra", payment.getPurchase().getId(),
                        "idUsuario", payment.getPurchase().getBuyer().getId()),
                String.class);

        if (!notaFiscalResponse.getStatusCode().is2xxSuccessful()) {
            System.out.println("ERRO ao gerar nota fiscal!");
        }
    }
}
