package br.com.atlas.atlas_logistics.adapters.persistence;

import br.com.atlas.atlas_logistics.domain.model.relationalModels.items.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductSearchRepository extends ElasticsearchRepository<Product, UUID> {
}
