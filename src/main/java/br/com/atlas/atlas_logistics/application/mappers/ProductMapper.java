package br.com.atlas.atlas_logistics.application.mappers;


import br.com.atlas.atlas_logistics.adapters.web.controller.dtos.response.product.ProductListDTO;
import br.com.atlas.atlas_logistics.adapters.web.controller.dtos.response.product.ProductListItemDTO;
import br.com.atlas.atlas_logistics.adapters.web.controller.dtos.response.product.ReturnProductDTO;
import br.com.atlas.atlas_logistics.application.intent.ProductPatch;
import br.com.atlas.atlas_logistics.adapters.web.controller.dtos.request.product.CreateProductDTO;
import br.com.atlas.atlas_logistics.adapters.web.controller.dtos.request.product.PatchProductDTO;
import br.com.atlas.atlas_logistics.domain.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public class ProductMapper {
    public Product toCreateProduct(CreateProductDTO createProductDTO){

        Product product = new Product(createProductDTO.name(), createProductDTO.sku(), createProductDTO.value());
        return product;
    }

    public ProductPatch toPatchProduct(PatchProductDTO patchProductDTO){
        return new ProductPatch(Optional.ofNullable(patchProductDTO.name()),Optional.ofNullable(patchProductDTO.sku()),Optional.ofNullable(patchProductDTO.value()));

    }

    public ReturnProductDTO toGetProduct (Product product){
        return new ReturnProductDTO(product.getName(), product.getSku(), product.getValue(),product.getId());
    }

    public ProductListItemDTO toListItem(Product product){
        return new ProductListItemDTO(product.getName(), product.getSku(), product.getValue(), product.getId());

    }

    public ProductListDTO toProductListDTO(Page<Product> page){
        List<ProductListItemDTO> items = page.getContent().stream().map(this::toListItem).toList();

        return new ProductListDTO(items,page.getNumber(),page.getSize(),page.getTotalElements(), page.getTotalPages());

    }



}
