package br.com.brunno.mercadoLivre.category;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/*
    Contagem de complexidade cognitiva
    (Classe sem estado - limite 7)

    - Category

    total: 1
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByNameIgnoreCase(String name);

}
