package br.com.brunno.mercadoLivre.payment;

import br.com.brunno.mercadoLivre.product.Product;
import br.com.brunno.mercadoLivre.purchase.Purchase;
import br.com.brunno.mercadoLivre.user.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/* Contagem carga intrinseca:
    -
 */

@Service
public class PaymentEmail implements SuccessPaymentListener, FailPaymentListener {

    @Override
    public void process(Payment payment) {
        if (payment.isSuccess()) {
            sendSuccessPayment(payment);
        } else {
            sendErrorEmail(payment);
        }
    }

    private void sendSuccessPayment(Payment payment) {
        Purchase purchase = payment.getPurchase();
        String purchaseId = purchase.getId();
        User buyer = purchase.getBuyer();
        Product product = purchase.getProduct();

        System.out.println("Enviando email...");
        System.out.println("Caro "+buyer.getLogin()+" a compra do produto "+product.getName()+" foi efetuada.\n"+
                "Segue as informações da compra:\n"+
                "id de compra: "+purchaseId+"\n"+
                "nome do produto: "+product.getName()+"\n"+
                "vendedor: "+product.getOwner().getLogin()+"\n"+
                "preço do produto: "+product.getPrice()+"\n"+
                "quantidade comprada: "+purchase.getQuantity()+"\n"+
                "valor total da compra: "+product.getPrice().multiply(BigDecimal.valueOf(purchase.getQuantity()))+"\n"+
                "data de pagamento: "+payment.getCreateDate());
    }

    private void sendErrorEmail(Payment payment) {
        Purchase purchase = payment.getPurchase();
        User buyer = purchase.getBuyer();
        Product product = purchase.getProduct();

        System.out.println("Pagamento com erro!");
        System.out.println("Caro "+buyer.getLogin()+" ocorreu uma falha no pagamento da compra do produto "+product.getName()+"\n"+
                "Acesse http://localhost:8080/purchase para tentar novamente.");
    }

}
