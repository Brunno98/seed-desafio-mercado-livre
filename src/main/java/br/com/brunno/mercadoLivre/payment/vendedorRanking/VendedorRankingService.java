package br.com.brunno.mercadoLivre.payment.vendedorRanking;

import br.com.brunno.mercadoLivre.payment.Payment;
import br.com.brunno.mercadoLivre.payment.SuccessPaymentListener;
import br.com.brunno.mercadoLivre.purchase.Purchase;
import br.com.brunno.mercadoLivre.user.User;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/* contagem carga intrinseca:
    - SuccessPaymentListener
    - Payment
    - Purchase
    - User

    total: 4
 */

@Service
public class VendedorRankingService implements SuccessPaymentListener {

    @Override
    public void process(Payment payment) {
        Assert.isTrue(payment.isSuccess(), "payment should be success");

        Purchase purchase = payment.getPurchase();
        User seller = purchase.getProduct().getOwner();
        new RestTemplate().postForEntity("http://localhost:8080/vendedor/ranking", Map.of(
                "idVenda", purchase.getId(),
                "idVendedor", seller.getId()
        ), String.class);
    }
}
