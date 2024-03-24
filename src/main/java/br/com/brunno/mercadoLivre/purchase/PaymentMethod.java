package br.com.brunno.mercadoLivre.purchase;

import java.text.MessageFormat;

public enum PaymentMethod {
    PAYPAL("paypal.com/%s?redirectUrl=%s"),
    PAGSEGURO("pagseguro.com?returnId=%s&redirectUrl=%s");

    private final String redirectLinkTemplate;
    PaymentMethod(String redirectLinkTemplate) {
        this.redirectLinkTemplate = redirectLinkTemplate;
    }

    public String getRedirectLink(String purchaseId, String callbackUrl) {
        return String.format(this.redirectLinkTemplate, purchaseId, callbackUrl);
    }


}
