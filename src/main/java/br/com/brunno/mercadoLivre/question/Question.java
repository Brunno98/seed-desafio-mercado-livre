package br.com.brunno.mercadoLivre.question;

import br.com.brunno.mercadoLivre.product.Product;
import br.com.brunno.mercadoLivre.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @NotNull
    private LocalDateTime createDate;

    @NotNull
    @ManyToOne
    private User author;

    @NotNull
    @ManyToOne
    private Product product;

    @Deprecated
    public Question() {}

    public Question(String title, User author, Product product) {
        Assert.isTrue(!product.belongsTo(author), "Author should not ask questions for own product");
        this.title = title;
        this.author = author;
        this.product = product;
        this.createDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public String getAuthorName() {
        return author.getLogin();
    }

    public String getProductName() {
        return product.getName();
    }

    public String getProductOwner() {
        return product.getOwnerLogin();
    }

    public Long getProductId() {
        return product.getId();
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", createDate=" + createDate +
                ", author=" + author.getLogin() +
                ", product=" + product.getName() +
                '}';
    }

    public Product getProduct() {
        return product;
    }
}
