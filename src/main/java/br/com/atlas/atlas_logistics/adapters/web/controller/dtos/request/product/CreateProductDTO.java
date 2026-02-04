package br.com.atlas.atlas_logistics.adapters.web.controller.dtos.request.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateProductDTO(@NotBlank String name, @NotBlank String sku, @NotNull BigDecimal value) {
}
