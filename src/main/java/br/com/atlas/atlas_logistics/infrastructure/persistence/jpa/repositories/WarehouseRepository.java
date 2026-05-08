package br.com.atlas.atlas_logistics.infrastructure.persistence.jpa.repositories;

import br.com.atlas.atlas_logistics.infrastructure.persistence.jpa.items.WarehouseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface WarehouseRepository extends JpaRepository<WarehouseEntity, UUID> {

    boolean existsByName(String name);

    Page<WarehouseEntity> findAll (Pageable pageable);
}
