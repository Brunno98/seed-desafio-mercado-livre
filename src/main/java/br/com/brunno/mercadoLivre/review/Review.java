package br.com.brunno.mercadoLivre.review;

import br.com.brunno.mercadoLivre.product.Product;
import br.com.brunno.mercadoLivre.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Range(min = 1, max = 5)
    private int rating;

    @NotBlank
    private String title;

    @NotBlank
    @Length(max = 500)
    private String description;

    @NotNull
    @ManyToOne
    private Product product;

    @NotNull
    @ManyToOne
    private User author;

    @Deprecated
    public Review() {}

    public Review(int rating, String title, String description, Product product, User author) {
        this.rating = rating;
        this.title = title;
        this.description = description;
        this.product = product;
        this.author = author;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", rating=" + rating +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", author='" + author.getLogin() + '\'' +
                ", product='" + product.getName() + '\'' +
                '}';
    }
}
