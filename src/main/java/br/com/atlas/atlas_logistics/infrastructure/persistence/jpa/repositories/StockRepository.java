package br.com.atlas.atlas_logistics.infrastructure.persistence.jpa.repositories;

import br.com.atlas.atlas_logistics.infrastructure.persistence.jpa.items.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface StockRepository extends JpaRepository<StockEntity, UUID> {
}
