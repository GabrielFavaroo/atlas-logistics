package br.com.atlas.atlas_logistics.application.mappers;

import br.com.atlas.atlas_logistics.api.dtos.request.warehouse.CreateWarehouseDTO;
import br.com.atlas.atlas_logistics.infrastructure.persistence.jpa.items.WarehouseEntity;
import org.springframework.stereotype.Component;

@Component
public class WarehouseMapper {
    public WarehouseEntity toCreateWarehouse(CreateWarehouseDTO createWarehouseDTO) {
        WarehouseEntity warehouseEntity = new WarehouseEntity(createWarehouseDTO.name(), createWarehouseDTO.cep(), createWarehouseDTO.stockEntity());
        return warehouseEntity;
    }
}
