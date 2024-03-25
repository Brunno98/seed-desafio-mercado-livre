package br.com.brunno.mercadoLivre.payment;

import br.com.brunno.mercadoLivre.notaFiscal.NotaFiscalService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
    Contagem carga intrinseca:
    - NotaFiscalService
    - PaymentEmail
    - PaymentCallbackRequest
    - Payment::isSuccess
    - Assert.isTrue(!hasSuccess...
    - Payment
    - UniquePaymentTransactionIdValidator

    total: 7
 */

@RestController
public class CallbackController {

    private final EntityManager entityManager;
    private final NotaFiscalService notaFiscalService;
    private final PaymentEmail paymentEmail;
    private final UniquePaymentTransactionIdValidator uniquePaymentTransactionIdValidator;

    @Autowired
    public CallbackController(EntityManager entityManager, NotaFiscalService notaFiscalService, PaymentEmail paymentEmail, UniquePaymentTransactionIdValidator uniquePaymentTransactionIdValidator) {
        this.entityManager = entityManager;
        this.notaFiscalService = notaFiscalService;
        this.paymentEmail = paymentEmail;
        this.uniquePaymentTransactionIdValidator = uniquePaymentTransactionIdValidator;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(uniquePaymentTransactionIdValidator);
    }

    @Transactional
    @PostMapping("/payment/callback")
    public String paymentCallback(@RequestBody @Valid PaymentCallbackRequest paymentCallbackRequest) throws BindException {
        List<Payment> transactions = entityManager.createQuery("SELECT p FROM Payment p WHERE p.purchase.id = :pId", Payment.class)
                .setParameter("pId", paymentCallbackRequest.getPurchaseId())
                .getResultList();

        boolean hasSuccess = transactions.stream().anyMatch(Payment::isSuccess);
        Assert.isTrue(!hasSuccess, "purchase already processed with success");

        Payment payment = paymentCallbackRequest.toPayment(entityManager);

        entityManager.persist(payment);

        // caso compra com sucesso, então chama serviço de nota fiscal
        // precisa de id da compra e id do usuario
        notaFiscalService.process(payment);

        // envia email para o comprador
        // caso compra tenha sido sucesso, então email tera o maximo de informacao que puder
        // caso compra tenha falhado, então email tera aviso de falha e link para retentar

        paymentEmail.process(payment);

        return payment.toString();
    }

}
