package br.com.brunno.mercadoLivre.category;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import org.springframework.util.Assert;

/*
    Contagem de complexidade cognitiva
    (Classe com estado - limite 9)

    - Category (referencia a si propria)

    total: 1
 */
/*
    Branches para testar: 0
 */


@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToOne
    private Category parent;


    @Deprecated
    public Category() {}

    public Category(String name) {
        this.name = name;
    }


    public void setParent(Category parentCategory) {
        Assert.notNull(parentCategory, "cannot set null as parent category");
        this.parent = parentCategory;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parent=" + parent +
                '}';
    }

    public String getName() {
        return name;
    }
}
