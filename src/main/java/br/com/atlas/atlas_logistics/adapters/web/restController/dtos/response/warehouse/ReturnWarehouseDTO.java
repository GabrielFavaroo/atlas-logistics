package br.com.atlas.atlas_logistics.adapters.web.restController.dtos.response.warehouse;

import br.com.atlas.atlas_logistics.domain.model.Stock;

import java.util.Set;
import java.util.UUID;

public record ReturnWarehouseDTO(String name, String cep, Set<Stock> stock , UUID id) {
}
