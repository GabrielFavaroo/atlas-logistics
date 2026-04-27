package br.com.atlas.atlas_logistics.adapters.persistence;

import br.com.atlas.atlas_logistics.domain.model.relationalModels.items.Warehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, UUID> {

    boolean existsByName(String name);

    Page<Warehouse> findAll (Pageable pageable);
}
