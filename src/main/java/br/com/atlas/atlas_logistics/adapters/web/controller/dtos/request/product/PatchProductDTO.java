package br.com.atlas.atlas_logistics.adapters.web.controller.dtos.request.product;


import jakarta.validation.constraints.Positive;
import org.jspecify.annotations.Nullable;

import java.math.BigDecimal;

public record PatchProductDTO(@Nullable String name,@Nullable String sku, @Positive @Nullable BigDecimal value) {
}
