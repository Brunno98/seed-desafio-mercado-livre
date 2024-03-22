package br.com.brunno.mercadoLivre.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*
    Contagem de carga intrinseca:
    (classe sem estado - 7 pontos):
    - ProductRepository
    - ProductDetails
    - ProductDetails::new
    - ProductFullDetails
    - if (optionalProduct.isEmpty())

    Total: 5
 */

/*
    branches para se testar: 1
 */

@RestController
@RequestMapping("/product")
public class ListProductController {

    private final ProductRepository productRepository;

    @Autowired
    public ListProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<ProductDetails> listProducts() {
        return productRepository.findAll().stream()
                .map(ProductDetails::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductFullDetails getProductById(@PathVariable Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product id " + id + " not found");
        }
        Product product = optionalProduct.get();

        return new ProductFullDetails(product);
    }

}
