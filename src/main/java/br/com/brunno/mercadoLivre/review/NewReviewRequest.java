package br.com.brunno.mercadoLivre.review;

import br.com.brunno.mercadoLivre.product.Product;
import br.com.brunno.mercadoLivre.user.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

@Getter
public class NewReviewRequest {

    @Range(min = 1, max = 5)
    private int rating;

    @NotBlank
    private String title;

    @NotBlank
    @Length(max = 500)
    private String description;

    public Review toReview(Product product, User author) {
        return new Review(
                rating,
                title,
                description,
                product,
                author
        );
    }
}
