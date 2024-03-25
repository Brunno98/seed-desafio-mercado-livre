package br.com.brunno.mercadoLivre.purchase;

public enum PaymentMethod {
    PAYPAL("paypal.com/%s?redirectUrl=%s", "1", "0"),
    PAGSEGURO("pagseguro.com?returnId=%s&redirectUrl=%s", "SUCESSO", "ERRO");

    private final String redirectLinkTemplate;
    private final String success;
    private final String fail;
    PaymentMethod(String redirectLinkTemplate, String success, String fail) {
        this.redirectLinkTemplate = redirectLinkTemplate;
        this.success = success;
        this.fail = fail;
    }

    public String getRedirectLink(String purchaseId, String callbackUrl) {
        return String.format(this.redirectLinkTemplate, purchaseId, callbackUrl);
    }

    public String getPaymentStatus(String status) {
        if (status.equals(success)) {
            return "SUCCESS";
        } else if (status.equals(fail)) {
            return "FAIL";
        }
        throw new IllegalArgumentException("value "+status+" is not a valid status of PaymentMethod "+this.name());
    }

}
