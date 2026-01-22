package br.com.atlas.atlas_logistics.application.usecase;

import br.com.atlas.atlas_logistics.domain.exception.BusinessException;
import br.com.atlas.atlas_logistics.domain.model.Product;
import br.com.atlas.atlas_logistics.domain.repository.ProductRepository;

import java.util.List;
import java.util.UUID;

public class ProductUseCase {

    private final ProductRepository productRepository;

    public ProductUseCase(ProductRepository productRepository){
        this.productRepository = productRepository;

    }




    public Product createProduct(Product product){
        if(productRepository.existsByName(product.getName())){
            throw new BusinessException("Produto já existe na base de dados");
        }
        else{
            productRepository.save(product);
        }
        return product;

    }

    public List<Product> listProducts(){
        return productRepository.findAll();

    }

    public Product getProduct(UUID id){
        return productRepository.findById(id).orElseThrow(() -> new BusinessException("Produto não encontrado na base de dados"));
    }


    public Product updateProduct(UUID id, Product updatedDataProduct){
        Product product = getProduct(id);

        if(!product.getName().equals(updatedDataProduct.getName())){
            product.setName(updatedDataProduct.getName());
        }

        if(product.getValue().compareTo(updatedDataProduct.getValue()) != 0){
            product.setValue(updatedDataProduct.getValue());
        }

        if(!product.getSku().equals(updatedDataProduct.getSku())){
            product.setSku(updatedDataProduct.getSku());
        }

        return productRepository.save(product);

    }

    public void deleteProduct(UUID id){
        Product product = getProduct(id);

        productRepository.delete(product);
    }


}
