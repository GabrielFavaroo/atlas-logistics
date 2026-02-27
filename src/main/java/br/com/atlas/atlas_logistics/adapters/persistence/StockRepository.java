package br.com.atlas.atlas_logistics.adapters.persistence;

import br.com.atlas.atlas_logistics.domain.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface StockRepository extends JpaRepository<Stock, UUID> {
}
