package br.com.atlas.atlas_logistics.api.dtos.response.warehouse;

import br.com.atlas.atlas_logistics.infrastructure.persistence.jpa.items.StockEntity;

import java.util.Set;
import java.util.UUID;

public record ReturnWarehouseDTO(String name, String cep, Set<StockEntity> stockEntity, UUID id) {
}
