package br.com.atlas.atlas_logistics.adapters.web.controller;


import br.com.atlas.atlas_logistics.adapters.web.assembly.GenericAssembler;
import br.com.atlas.atlas_logistics.adapters.web.controller.dtos.request.product.CreateProductDTO;
import br.com.atlas.atlas_logistics.adapters.web.controller.dtos.request.product.PatchProductDTO;

import br.com.atlas.atlas_logistics.adapters.web.controller.dtos.response.product.ProductListDTO;
import br.com.atlas.atlas_logistics.adapters.web.controller.dtos.response.product.ProductListItemDTO;
import br.com.atlas.atlas_logistics.adapters.web.controller.dtos.response.product.ReturnProductDTO;

import br.com.atlas.atlas_logistics.application.usecase.ProductUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.function.Function;

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
    @Operation(summary = "Endpoints para administrar produtos",description = "Endpoints para administrar produtos",
    tags = {"products","items"}
    ,responses = {
                  @ApiResponse(description = "Success",responseCode = "200", content = {
                          @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = CollectionModel.class)))}),
                  @ApiResponse(description = "No content",responseCode = "204", content = @Content),
                  @ApiResponse(description = "Unauthorized",responseCode = "401", content = @Content),
                  @ApiResponse(description = "Bad request",responseCode = "400", content = @Content),
                  @ApiResponse(description = "Not found",responseCode = "404", content = @Content),
                  @ApiResponse(description = "Internal Server Error",responseCode = "500", content = @Content)




    }
    )

    @PostMapping
    public ResponseEntity<EntityModel<ReturnProductDTO>> save(@RequestBody @Valid CreateProductDTO createProductDTO){

        ReturnProductDTO returnProductDTO = productUseCase.createProduct(createProductDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(genericAssembler.toModel(returnProductDTO, ProductController.class, returnProductDTO.id()));
    }


   @DeleteMapping("/{id}")
   public ResponseEntity<Void> delete(@PathVariable(value = "id") UUID id){
        productUseCase.deleteProduct(id);
        return ResponseEntity.noContent().build();
   }



   @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ReturnProductDTO>> update(@PathVariable(value="id") UUID id,@RequestBody @Valid CreateProductDTO createProductDTO){

        ReturnProductDTO returnProductDTO = productUseCase.updateProductCompletely(id,createProductDTO);

        return ResponseEntity.status(HttpStatus.OK).body(genericAssembler.toModel(returnProductDTO, ProductController.class,returnProductDTO.id()));
   }


   @PatchMapping("/{id}")
   public ResponseEntity<EntityModel<ReturnProductDTO>> patch(@PathVariable(value = "id") UUID id, @RequestBody @Valid PatchProductDTO patchProductDTO){

        ReturnProductDTO returnProductDTO = productUseCase.updateProductPartially(id,patchProductDTO);


        return ResponseEntity.status(HttpStatus.OK).body(genericAssembler.toModel(returnProductDTO, ProductController.class,returnProductDTO.id()));
   }



   @GetMapping
   public ResponseEntity<CollectionModel<EntityModel<ProductListItemDTO>>> getAll(@RequestParam int page, @RequestParam int items){

       ProductListDTO productListDTO = productUseCase.listProducts(page,items);



        return ResponseEntity.ok(genericAssembler.toListModel(productListDTO.items(),ProductController.class,ProductListItemDTO::id));
   }



    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ReturnProductDTO>> getOne(@PathVariable(value = "id") UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(genericAssembler.toModel(productUseCase.getProductForRead(id), ProductController.class,id));
    }


}
