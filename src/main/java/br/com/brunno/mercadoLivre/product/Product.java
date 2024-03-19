package br.com.brunno.mercadoLivre.product;

import br.com.brunno.mercadoLivre.category.Category;
import br.com.brunno.mercadoLivre.user.User;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.List;

/*
    Contagem de carga intrinseca:
    (classe com estado - 9 pontos):
    - Category

    Total: 1
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
    private int quantityAvailable;

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

    @Deprecated
    public Product() {}

    public Product(String name,
                   BigDecimal price,
                   int quantityAvailable,
                   List<String> characteristics,
                   String description,
                   Category category,
                   User owner
    ) {
        this.name = name;
        this.price = price;
        this.quantityAvailable = quantityAvailable;
        this.characteristics = characteristics;
        this.description = description;
        this.category = category;
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantityAvailable=" + quantityAvailable +
                ", characteristics=" + characteristics +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", owner=" + owner +
                '}';
    }
}
