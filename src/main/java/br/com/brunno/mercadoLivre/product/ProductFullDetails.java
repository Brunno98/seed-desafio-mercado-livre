package br.com.brunno.mercadoLivre.product;

import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;


@Getter
public class ProductFullDetails {

    private CategoryDetails category;
    private String name;
    private double rating;
    private int availableQuantity;
    private String owner;
    private List<String> images;
    private List<String> characteristics;
    private String description;
    private List<ReviewDetails> reviews;

    public ProductFullDetails(Product product) {
        this.category = new CategoryDetails(product.getCategory());
        this.name = product.getName();
        this.rating = product.getRating();
        this.availableQuantity = product.getAvailableQuantity();
        this.owner = product.getOwnerLogin();
        this.images = product.getImages();
        this.characteristics = product.getCharacteristics();
        this.description = product.getDescription();
        this.reviews = product.mapReviews(ReviewDetails::new);
    }
}
