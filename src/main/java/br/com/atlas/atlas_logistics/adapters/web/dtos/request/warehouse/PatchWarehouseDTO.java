package br.com.atlas.atlas_logistics.adapters.web.dtos.request.warehouse;

import br.com.atlas.atlas_logistics.domain.model.relationalModels.items.Stock;
import org.jspecify.annotations.Nullable;

import java.util.Set;

public record PatchWarehouseDTO(@Nullable String name, @Nullable String cep, @Nullable Set<Stock> stock) {
}
