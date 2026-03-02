package br.com.atlas.atlas_logistics.application.usecase;


import br.com.atlas.atlas_logistics.adapters.web.controller.dtos.request.product.CreateProductDTO;
import br.com.atlas.atlas_logistics.adapters.web.controller.dtos.request.product.PatchProductDTO;
import br.com.atlas.atlas_logistics.adapters.web.controller.dtos.response.product.ProductListDTO;

import br.com.atlas.atlas_logistics.adapters.web.controller.dtos.response.product.ReturnProductDTO;
import br.com.atlas.atlas_logistics.application.intent.ProductPatch;
import br.com.atlas.atlas_logistics.application.mappers.ProductMapper;
import br.com.atlas.atlas_logistics.domain.exception.BusinessException;
import br.com.atlas.atlas_logistics.domain.model.Product;
import br.com.atlas.atlas_logistics.adapters.persistence.ProductRepository;
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




    public void createProduct(CreateProductDTO createProductDTO){

        Product product = productMapper.toCreateProduct(createProductDTO);
        if(productRepository.existsByName(product.getName())){
            throw new BusinessException("Produto já existe na base de dados");
        }
        else{
            productRepository.save(product);
        }

    }


    public ProductListDTO listProducts(int page, int items){
        Page<Product> list = productRepository.findAll(PageRequest.of(page,items));

        return productMapper.toProductListDTO(list);
    }



    public Product getProductById(UUID id){
        Product product = productRepository.findById(id).orElseThrow(() -> new BusinessException("Produto não encontrado na base de dados"));
        return product;
    }

    public ReturnProductDTO getProductForRead(UUID id){
        Product product = productRepository.findById(id).orElseThrow(() -> new BusinessException("Produto não encontrado na base de dados"));
        return productMapper.toGetProduct(product);
    }



    public void updateProductPartially(UUID id, PatchProductDTO patchProductDTO){

        ProductPatch updatedDataProduct = productMapper.toPatchProduct(patchProductDTO);

        Product product = getProductById(id);

        updatedDataProduct.getName().ifPresent(product::setName);
        updatedDataProduct.getValue().ifPresent(product::setValue);
        updatedDataProduct.getSku().ifPresent(product::setSku);


        productRepository.save(product);

    }

    public void updateProductCompletely(UUID id, CreateProductDTO createProductDTO){

        Product updatedDataProduct = productMapper.toCreateProduct(createProductDTO);
        Product product = getProductById(id);

        product.setName(updatedDataProduct.getName());
        product.setSku(updatedDataProduct.getSku());
        product.setValue(updatedDataProduct.getValue());

        productRepository.save(product);



    }



    public void deleteProduct(UUID id){
        Product product = getProductById(id);

        productRepository.delete(product);
    }


}
