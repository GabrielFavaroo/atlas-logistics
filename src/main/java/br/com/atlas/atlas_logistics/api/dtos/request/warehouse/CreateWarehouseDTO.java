package br.com.atlas.atlas_logistics.api.dtos.request.warehouse;

import br.com.atlas.atlas_logistics.infrastructure.persistence.jpa.items.StockEntity;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record CreateWarehouseDTO(@NotBlank String name, @NotBlank String cep, Set<StockEntity> stockEntity) {
}
