package br.com.atlas.atlas_logistics.adapters.web.controller;


import br.com.atlas.atlas_logistics.adapters.web.controller.dtos.request.product.CreateProductDTO;
import br.com.atlas.atlas_logistics.adapters.web.controller.dtos.request.product.PatchProductDTO;

import br.com.atlas.atlas_logistics.adapters.web.controller.dtos.response.product.ProductListDTO;
import br.com.atlas.atlas_logistics.adapters.web.controller.dtos.response.product.ReturnProductDTO;

import br.com.atlas.atlas_logistics.application.usecase.ProductUseCase;
import br.com.atlas.atlas_logistics.domain.model.Product;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductUseCase productUseCase;

    public ProductController(ProductUseCase productUseCase){
        this.productUseCase = productUseCase;
    }


    @PostMapping
    public ResponseEntity<Void> saveProduct(@RequestBody @Valid CreateProductDTO createProductDTO){

        productUseCase.createProduct(createProductDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


   @DeleteMapping("/{id}")
   public ResponseEntity<Void> deleteProduct(@PathVariable(value = "id") UUID id){
        productUseCase.deleteProduct(id);
        return ResponseEntity.noContent().build();
   }



   @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable(value="id") UUID id,@RequestBody @Valid CreateProductDTO createProductDTO){

        productUseCase.updateProductCompletely(id,createProductDTO);

        return ResponseEntity.status(HttpStatus.OK).build();
   }


   @PatchMapping("/{id}")
   public ResponseEntity<Void> patchProduct(@PathVariable(value = "id") UUID id, @RequestBody @Valid PatchProductDTO patchProductDTO){

        productUseCase.updateProductPartially(id,patchProductDTO);


        return ResponseEntity.status(HttpStatus.OK).build();
   }



   @GetMapping
   public ResponseEntity<ProductListDTO> getAllProducts(@RequestParam int page, @RequestParam int items){

        return ResponseEntity.status(HttpStatus.OK).body(productUseCase.listProducts(page,items));
   }



    @GetMapping("/{id}")
    public ResponseEntity<ReturnProductDTO> getOneProduct(@PathVariable(value = "id") UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(productUseCase.getProductForRead(id));
    }





}
