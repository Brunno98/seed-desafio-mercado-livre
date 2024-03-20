package br.com.brunno.mercadoLivre.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ListProductController {

    private final ProductRepository productRepository;

    @Autowired
    public ListProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/product")
    public List<ProductDetails> listProducts() {
        return productRepository.findAll().stream()
                .map(ProductDetails::new)
                .collect(Collectors.toList());
    }
}
