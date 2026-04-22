package br.com.atlas.atlas_logistics.adapters.web.dtos.response.product;

import java.util.List;

public record ProductListDTO(List<ProductListItemDTO> items , int page, int size, long totalElements, int totalPages) {}
