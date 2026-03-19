package br.com.atlas.atlas_logistics.adapters.web.controller;

import br.com.atlas.atlas_logistics.adapters.web.controller.dtos.request.warehouse.CreateWarehouseDTO;
import br.com.atlas.atlas_logistics.adapters.web.controller.dtos.response.warehouse.ReturnWarehouseDTO;
import br.com.atlas.atlas_logistics.adapters.web.controller.dtos.response.warehouse.WarehouseListDTO;
import br.com.atlas.atlas_logistics.application.usecase.WarehouseUseCase;
import jakarta.validation.Valid;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/warehouse")
public class WarehouseController {

    private final WarehouseUseCase warehouseUseCase;


    public WarehouseController(WarehouseUseCase warehouseUseCase) {
        this.warehouseUseCase = warehouseUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> createWarehouse(@RequestBody @Valid CreateWarehouseDTO createWarehouseDTO){
        warehouseUseCase.createWarehouse(createWarehouseDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWarehouse(@PathVariable UUID id){
        warehouseUseCase.deleteWarehouse(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateWarehouse(@PathVariable UUID id ,@RequestBody @Valid CreateWarehouseDTO createWarehouseDTO){
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> patchWarehouse(@PathVariable UUID id){
        return ResponseEntity.ok().build();
    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<ReturnWarehouseDTO> getOneWarehouse(@PathVariable UUID id){
//        ReturnWarehouseDTO returnWarehouseDTO
//        return ResponseEntity.status(HttpStatus.OK).body(returnWarehouseDTO);
//    }
//
//    @GetMapping
//    public ResponseEntity<WarehouseListDTO> getAllWarehouses(@PathVariable int page ){
//        return ResponseEntity.status(HttpStatus.OK).body(warehouseListDTO);
//    }
//


}
