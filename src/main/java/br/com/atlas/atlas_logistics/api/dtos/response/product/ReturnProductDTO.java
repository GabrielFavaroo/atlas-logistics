package br.com.atlas.atlas_logistics.api.dtos.response.product;

import java.math.BigDecimal;
import java.util.UUID;

public record ReturnProductDTO(String name, String Sku, BigDecimal value, UUID id) {
}
