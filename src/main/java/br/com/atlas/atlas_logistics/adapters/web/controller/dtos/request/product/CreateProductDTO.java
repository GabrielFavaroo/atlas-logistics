package br.com.atlas.atlas_logistics.adapters.web.controller.dtos.request.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

public record CreateProductDTO(@NotBlank String name, @NotBlank String sku, @Positive @NotNull BigDecimal value) {
}
