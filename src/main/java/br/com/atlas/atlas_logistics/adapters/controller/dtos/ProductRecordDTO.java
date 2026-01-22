package br.com.atlas.atlas_logistics.adapters.controller.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRecordDTO(@NotBlank String name, @NotBlank String sku, @NotNull BigDecimal value) {
}
