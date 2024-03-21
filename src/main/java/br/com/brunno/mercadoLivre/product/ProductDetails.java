package br.com.brunno.mercadoLivre.product;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
public class ProductDetails {
    private final Long id;
    private final String name;
    private final BigDecimal price;
    private final int availableQuantity;
    private final List<String> characteristics;
    private final String description;
    private final String category;
    private final String owner;
    private final List<String> images;

    public ProductDetails(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.availableQuantity = product.getAvailableQuantity();
        this.characteristics = product.getCharacteristics();
        this.description = product.getDescription();
        this.category = product.getCategoryName();
        this.owner = product.getOwnerLogin();
        this.images = product.getImages();
    }
}
