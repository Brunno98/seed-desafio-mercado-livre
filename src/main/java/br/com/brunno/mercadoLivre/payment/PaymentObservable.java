package br.com.brunno.mercadoLivre.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/* Contagem carga intrinseca:
    - SuccessPaymentListener
    - FailPaymentListener
    - Payment
    - listener -> listener.process(payment)
    - listener -> listener.process(payment)

    total: 5
 */

@Component
public class PaymentObservable {

    private final Set<SuccessPaymentListener> successObservable;
    private final Set<FailPaymentListener> failObservable;

    @Autowired
    public PaymentObservable(Set<SuccessPaymentListener> successObservable, Set<FailPaymentListener> failObservable) {
        this.successObservable = successObservable;
        this.failObservable = failObservable;
    }

    public void notify(Payment payment) {
        if (payment.isSuccess()) {
            successObservable.forEach(listener -> listener.process(payment));
        } else {
            failObservable.forEach(listener -> listener.process(payment));
        }
    }

}
