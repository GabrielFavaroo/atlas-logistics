package br.com.atlas.atlas_logistics.adapters.persistence;

import br.com.atlas.atlas_logistics.domain.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    boolean existsByName(String name);

    Page<Product> findAll(Pageable pageable);






}
