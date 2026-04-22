package br.com.atlas.atlas_logistics.adapters.web.dtos.response.product;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductListItemDTO(String name, String sku, BigDecimal value, UUID id) {
}
