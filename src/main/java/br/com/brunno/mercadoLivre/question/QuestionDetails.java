package br.com.brunno.mercadoLivre.question;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class QuestionDetails {

    private Long id;
    private String title;
    private LocalDateTime createDate;
    private String author;
    private String product;
    private String productOwner;

    public QuestionDetails(Question question) {
        this.id = question.getId();
        this.title = question.getTitle();
        this.createDate = question.getCreateDate();
        this.author = question.getAuthorName();
        this.product = question.getProductName();
        this.productOwner = question.getProductOwner();
    }
}
