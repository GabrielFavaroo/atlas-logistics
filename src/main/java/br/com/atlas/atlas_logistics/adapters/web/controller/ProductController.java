package br.com.atlas.atlas_logistics.adapters.web.controller;


import br.com.atlas.atlas_logistics.adapters.web.OpenApi.AtlasMultipleReturnOperation;
import br.com.atlas.atlas_logistics.adapters.web.OpenApi.AtlasSingleReturnOperation;
import br.com.atlas.atlas_logistics.adapters.web.assembly.GenericAssembler;
import br.com.atlas.atlas_logistics.adapters.web.controller.dtos.request.product.CreateProductDTO;
import br.com.atlas.atlas_logistics.adapters.web.controller.dtos.request.product.PatchProductDTO;

import br.com.atlas.atlas_logistics.adapters.web.controller.dtos.response.product.ProductListDTO;
import br.com.atlas.atlas_logistics.adapters.web.controller.dtos.response.product.ProductListItemDTO;
import br.com.atlas.atlas_logistics.adapters.web.controller.dtos.response.product.ReturnProductDTO;

import br.com.atlas.atlas_logistics.application.usecase.ProductUseCase;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/products")
@Tag(name = "products",description = "Endpoints para administrar produtos")
public class ProductController implements CrudInterface<CreateProductDTO,PatchProductDTO,ProductListDTO, ProductListItemDTO,ReturnProductDTO,UUID>{

    private final ProductUseCase productUseCase;
    private final GenericAssembler genericAssembler;

    public ProductController(ProductUseCase productUseCase, GenericAssembler genericAssembler){
        this.productUseCase = productUseCase;
        this.genericAssembler = genericAssembler;
    }

    @AtlasSingleReturnOperation(summary = "Salva um produto na base de dados e retorna-o",implementation = ReturnProductDTO.class)
    @PostMapping
    public ResponseEntity<EntityModel<ReturnProductDTO>> save(@RequestBody @Valid CreateProductDTO createProductDTO){

        ReturnProductDTO returnProductDTO = productUseCase.createProduct(createProductDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(genericAssembler.toModel(returnProductDTO, ProductController.class, returnProductDTO.id()));
    }

    @AtlasSingleReturnOperation(summary = "Apaga um produto da base de dados")
   @DeleteMapping("/{id}")
   public ResponseEntity<Void> delete(@PathVariable(value = "id") UUID id){
        productUseCase.deleteProduct(id);
        return ResponseEntity.noContent().build();
   }


   @AtlasSingleReturnOperation(summary = "Atualiza todos os campos de um produto", implementation = ReturnProductDTO.class)
   @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ReturnProductDTO>> update(@PathVariable(value="id") UUID id,@RequestBody @Valid CreateProductDTO createProductDTO){

        ReturnProductDTO returnProductDTO = productUseCase.updateProductCompletely(id,createProductDTO);

        return ResponseEntity.status(HttpStatus.OK).body(genericAssembler.toModel(returnProductDTO, ProductController.class,returnProductDTO.id()));
   }

    @AtlasSingleReturnOperation(summary = "Atualiza somente os campos fornecidos de um produto", implementation = ReturnProductDTO.class)
   @PatchMapping("/{id}")
   public ResponseEntity<EntityModel<ReturnProductDTO>> patch(@PathVariable(value = "id") UUID id, @RequestBody @Valid PatchProductDTO patchProductDTO){

        ReturnProductDTO returnProductDTO = productUseCase.updateProductPartially(id,patchProductDTO);


        return ResponseEntity.status(HttpStatus.OK).body(genericAssembler.toModel(returnProductDTO, ProductController.class,returnProductDTO.id()));
   }


    @AtlasMultipleReturnOperation(summary = "Retorna uma parcela de items da base de dados baseado na filtragem estipulada pelo usuario", implementation = ProductListItemDTO.class)
   @GetMapping
   public ResponseEntity<CollectionModel<EntityModel<ProductListItemDTO>>> getAll(@RequestParam int page, @RequestParam int items){

       ProductListDTO productListDTO = productUseCase.listProducts(page,items);



        return ResponseEntity.ok(genericAssembler.toListModel(productListDTO.items(),ProductController.class,ProductListItemDTO::id));
   }


    @AtlasSingleReturnOperation(summary = "Retorna um item referente ao id fornecido", implementation = ReturnProductDTO.class)
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ReturnProductDTO>> getOne(@PathVariable(value = "id") UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(genericAssembler.toModel(productUseCase.getProductForRead(id), ProductController.class,id));
    }


}
