package br.com.brunno.mercadoLivre.product;

import br.com.brunno.mercadoLivre.review.Review;
import lombok.Getter;

@Getter
public class ReviewDetails {

    private int rating;
    private String title;
    private String description;
    private String author;

    public ReviewDetails(Review review) {
        this.rating = review.getRating();
        this.title = review.getTitle();
        this.description = review.getDescription();
        this.author = review.getAuthor();
    }
}
