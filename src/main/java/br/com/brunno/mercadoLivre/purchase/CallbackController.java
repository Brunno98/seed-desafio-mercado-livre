package br.com.brunno.mercadoLivre.purchase;

import br.com.brunno.mercadoLivre.product.Product;
import br.com.brunno.mercadoLivre.user.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

@RestController
public class CallbackController {

    private final EntityManager entityManager;

    @Autowired
    public CallbackController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    @PostMapping("/payment/callback")
    public String paymentCallback(@RequestBody @Valid PaymentCallbackRequest paymentCallbackRequest) throws BindException {
        Payment payment = paymentCallbackRequest.toPayment(entityManager);

        entityManager.persist(payment);

        // caso compra com sucesso, então chama serviço de nota fiscal
        // precisa de id da compra e id do usuario
        Purchase purchase = payment.getPurchase();
        String purchaseId = purchase.getId();
        User buyer = purchase.getBuyer();
        Long buyerId = buyer.getId();
        if (payment.isSuccess()) {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> notaFiscalResponse = restTemplate.postForEntity("http://localhost:8080/nota-fiscal", Map.of(
                            "idCompra", purchaseId,
                            "idUsuario", buyerId),
                    String.class);
            if (!notaFiscalResponse.getStatusCode().is2xxSuccessful()) {
                System.out.println("ERRO ao gerar nota fiscal!");
            }
        }

        Product product = purchase.getProduct();
        /*
        User seller = product.getOwner();
        Long sellerId = seller.getId();
        */

        // envia email para o comprador
        // caso compra tenha sido sucesso, então email tera o maximo de informacao que puder
        // caso compra tenha falhado, então email tera aviso de falha e link para retentar

        if (payment.isSuccess()) {
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
        } else {
            System.out.println("Pagamento com erro!");
            System.out.println("Caro "+buyer.getLogin()+" ocorreu uma falha no pagamento da compra do produto "+product.getName()+"\n"+
                    "Acesse http://localhost:8080/purchase para tentar novamente.");
        }

        return payment.toString();
    }

}
