package br.com.atlas.atlas_logistics.application.mappers;


import br.com.atlas.atlas_logistics.api.dtos.response.product.ProductListDTO;
import br.com.atlas.atlas_logistics.api.dtos.response.product.ProductListItemDTO;
import br.com.atlas.atlas_logistics.api.dtos.response.product.ReturnProductDTO;
import br.com.atlas.atlas_logistics.application.intent.ProductPatch;
import br.com.atlas.atlas_logistics.api.dtos.request.product.CreateProductDTO;
import br.com.atlas.atlas_logistics.api.dtos.request.product.PatchProductDTO;
import br.com.atlas.atlas_logistics.infrastructure.persistence.jpa.items.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProductMapper {
    public ProductEntity toCreateProduct(CreateProductDTO createProductDTO){

        return new ProductEntity(createProductDTO.name(), createProductDTO.sku(), createProductDTO.value());
    }

    public ProductPatch toPatchProduct(PatchProductDTO patchProductDTO){
        return new ProductPatch(Optional.ofNullable(patchProductDTO.name()),Optional.ofNullable(patchProductDTO.sku()),Optional.ofNullable(patchProductDTO.value()));

    }

    public ReturnProductDTO toGetProduct (ProductEntity productEntity){
        return new ReturnProductDTO(productEntity.getName(), productEntity.getSku(), productEntity.getValue(), productEntity.getId());
    }

    public ProductListItemDTO toListItem(ProductEntity productEntity){
        return new ProductListItemDTO(productEntity.getName(), productEntity.getSku(), productEntity.getValue(), productEntity.getId());

    }

    public ProductListDTO toProductListDTO(Page<ProductEntity> page){
        List<ProductListItemDTO> items = page.getContent().stream().map(this::toListItem).toList();

        return new ProductListDTO(items,page.getNumber(),page.getSize(),page.getTotalElements(), page.getTotalPages());

    }



}
