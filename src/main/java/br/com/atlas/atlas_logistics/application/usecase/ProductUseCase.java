package br.com.atlas.atlas_logistics.application.usecase;


import br.com.atlas.atlas_logistics.api.dtos.request.product.CreateProductDTO;
import br.com.atlas.atlas_logistics.api.dtos.request.product.PatchProductDTO;
import br.com.atlas.atlas_logistics.api.dtos.response.product.ProductListDTO;

import br.com.atlas.atlas_logistics.api.dtos.response.product.ReturnProductDTO;
import br.com.atlas.atlas_logistics.application.intent.ProductPatch;
import br.com.atlas.atlas_logistics.application.mappers.ProductMapper;
import br.com.atlas.atlas_logistics.domain.exception.BusinessException;
import br.com.atlas.atlas_logistics.infrastructure.persistence.jpa.items.ProductEntity;
import br.com.atlas.atlas_logistics.infrastructure.persistence.jpa.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductUseCase {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductUseCase(ProductRepository productRepository,ProductMapper productMapper){
        this.productRepository = productRepository;
        this.productMapper = productMapper;

    }




    public ReturnProductDTO createProduct(CreateProductDTO createProductDTO){

        ProductEntity productEntity = productMapper.toCreateProduct(createProductDTO);
        if(productRepository.existsByName(productEntity.getName())){
            throw new BusinessException("Produto já existe na base de dados");
        }
        else{
            productRepository.save(productEntity);
        }
        return productMapper.toGetProduct(productEntity);

    }


    public ProductListDTO listProducts(int page, int items){
        Page<ProductEntity> list = productRepository.findAll(PageRequest.of(page,items));

        return productMapper.toProductListDTO(list);
    }



    public ProductEntity getProductById(UUID id){
        ProductEntity productEntity = productRepository.findById(id).orElseThrow(() -> new BusinessException("Produto não encontrado na base de dados"));
        return productEntity;
    }

    public ReturnProductDTO getProductForRead(UUID id){
        ProductEntity productEntity = productRepository.findById(id).orElseThrow(() -> new BusinessException("Produto não encontrado na base de dados"));
        return productMapper.toGetProduct(productEntity);
    }



    public ReturnProductDTO updateProductPartially(UUID id, PatchProductDTO patchProductDTO){

        ProductPatch updatedDataProduct = productMapper.toPatchProduct(patchProductDTO);

        ProductEntity productEntity = getProductById(id);

        updatedDataProduct.getName().ifPresent(productEntity::setName);
        updatedDataProduct.getValue().ifPresent(productEntity::setValue);
        updatedDataProduct.getSku().ifPresent(productEntity::setSku);


        productRepository.save(productEntity);
        return productMapper.toGetProduct(productEntity);

    }

    public ReturnProductDTO updateProductCompletely(UUID id, CreateProductDTO createProductDTO){

        ProductEntity updatedDataProductEntity = productMapper.toCreateProduct(createProductDTO);
        ProductEntity productEntity = getProductById(id);

        productEntity.setName(updatedDataProductEntity.getName());
        productEntity.setSku(updatedDataProductEntity.getSku());
        productEntity.setValue(updatedDataProductEntity.getValue());

        productRepository.save(productEntity);

        return productMapper.toGetProduct(productEntity);

    }



    public void deleteProduct(UUID id){
        ProductEntity productEntity = getProductById(id);

        productRepository.delete(productEntity);
    }


}
