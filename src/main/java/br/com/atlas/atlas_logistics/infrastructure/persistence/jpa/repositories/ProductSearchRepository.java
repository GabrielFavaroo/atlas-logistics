package br.com.atlas.atlas_logistics.infrastructure.persistence.jpa.repositories;

import br.com.atlas.atlas_logistics.infrastructure.persistence.jpa.items.ProductEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductSearchRepository extends ElasticsearchRepository<ProductEntity, UUID> {
}
