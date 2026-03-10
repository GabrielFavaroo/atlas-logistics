package br.com.atlas.atlas_logistics.adapters.web.controller.dtos.response.product;

import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.util.UUID;

public record ReturnProductDTO(String name, String Sku, BigDecimal value, UUID id) {
}
