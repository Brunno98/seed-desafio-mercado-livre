package br.com.brunno.mercadoLivre.purchase;

import org.springframework.stereotype.Service;

@Service
public class EmailSender {

    public void sendEmail(String text) {
        System.out.println("Enviando email...");
        System.out.println(text);
    };
}
