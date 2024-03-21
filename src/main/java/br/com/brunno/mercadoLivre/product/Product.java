package br.com.brunno.mercadoLivre.product;

import br.com.brunno.mercadoLivre.category.Category;
import br.com.brunno.mercadoLivre.review.Review;
import br.com.brunno.mercadoLivre.user.User;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
    Contagem de carga intrinseca:
    (classe com estado - 9 pontos):
    - Category
    - User

    Total: 2
 */

/*
    branches para se testar: 0
 */

@Entity
@Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    @Positive
    private BigDecimal price;

    @Positive
    private int availableQuantity;

    @ElementCollection
    @Size(min = 3)
    private List<@NotBlank String> characteristics;

    @NotBlank
    @Length(max = 1000)
    private String description;

    @ManyToOne
    private Category category;

    @ManyToOne
    private User owner;

    @ElementCollection
    private List<@NotBlank String> images;

    @OneToMany(mappedBy = "product")
    private List<Review> reviews;

    @Deprecated
    public Product() {}

    public Product(String name,
                   BigDecimal price,
                   int availableQuantity,
                   List<String> characteristics,
                   String description,
                   Category category,
                   User owner
    ) {
        this.name = name;
        this.price = price;
        this.availableQuantity = availableQuantity;
        this.characteristics = characteristics;
        this.description = description;
        this.category = category;
        this.owner = owner;
        this.images = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantityAvailable=" + availableQuantity +
                ", characteristics=" + characteristics +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", owner=" + owner +
                ", images=" + images +
                '}';
    }

    public String getCategoryName() {
        Assert.notNull(category, "Category from product should did not be null!");
        return category.getName();
    }

    public String getOwnerLogin() {
        Assert.notNull(owner, "Product owner should did not be null!");
        return owner.getLogin();
    }

    public void addImage(String imageUri) {
        this.images.add(imageUri);
    }

    public boolean belongsTo(User user) {
        Assert.notNull(user, "user to verify if is product owner cannot be null!");
        return user.equals(this.owner);
    }

    public double getRating() {
        return reviews.stream().mapToInt(Review::getRating).average().orElse(0);
    }

    public List<Review> getReviews() {
        return Collections.unmodifiableList(this.reviews);
    }
}
