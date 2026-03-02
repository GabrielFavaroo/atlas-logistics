package br.com.atlas.atlas_logistics.adapters.web.controller.dtos.request.warehouse;

import br.com.atlas.atlas_logistics.domain.model.Stock;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record CreateWarehouseDTO(@NotBlank String name, @NotBlank String cep, Set<Stock> stock) {
}
