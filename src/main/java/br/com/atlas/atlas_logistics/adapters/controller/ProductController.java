package br.com.atlas.atlas_logistics.adapters.controller;


import br.com.atlas.atlas_logistics.adapters.controller.dtos.ProductRecordDTO;
import br.com.atlas.atlas_logistics.application.usecase.ProductUseCase;
import br.com.atlas.atlas_logistics.domain.model.Product;
import br.com.atlas.atlas_logistics.domain.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductUseCase productUseCase;

    public ProductController(ProductUseCase productUseCase){
        this.productUseCase = productUseCase;
    }


    @PostMapping
    public ResponseEntity<Product> saveProduct(@RequestBody @Valid ProductRecordDTO productRecordDTO){

        Product product = new Product(productRecordDTO.name(),productRecordDTO.sku(),productRecordDTO.value());

        Product response = productUseCase.createProduct(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


   @DeleteMapping("/{id}")
   public ResponseEntity<Product> deleteProduct(@PathVariable(value = "id") UUID id){
        productUseCase.deleteProduct(id);
        return ResponseEntity.noContent().build();
   }



   @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable(value="id") UUID id,@RequestBody @Valid ProductRecordDTO productRecordDTO){

        Product updatedProduct = new Product(productRecordDTO.name(),productRecordDTO.sku(),productRecordDTO.value());

        Product response = productUseCase.updateProduct(id,updatedProduct);

        return ResponseEntity.status(HttpStatus.OK).body(response);
   }



   @GetMapping
   public ResponseEntity<List<Product>> getAllProducts(){

        return ResponseEntity.status(HttpStatus.OK).body(productUseCase.listProducts());
   }



    @GetMapping("/{id}")
    public ResponseEntity<Product> getOneProduct(@PathVariable(value = "id") UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(productUseCase.getProduct(id));
    }





}
