package br.com.atlas.atlas_logistics.api.dtos.request.warehouse;

import br.com.atlas.atlas_logistics.infrastructure.persistence.jpa.items.StockEntity;
import org.jspecify.annotations.Nullable;

import java.util.Set;

public record PatchWarehouseDTO(@Nullable String name, @Nullable String cep, @Nullable Set<StockEntity> stockEntity) {
}
