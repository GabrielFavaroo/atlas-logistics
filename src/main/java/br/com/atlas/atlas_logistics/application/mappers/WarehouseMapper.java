package br.com.atlas.atlas_logistics.application.mappers;

import br.com.atlas.atlas_logistics.adapters.web.controller.dtos.request.warehouse.CreateWarehouseDTO;
import br.com.atlas.atlas_logistics.domain.model.Warehouse;

public class WarehouseMapper {
    public Warehouse toCreateWarehouse(CreateWarehouseDTO createWarehouseDTO) {
        Warehouse warehouse = new Warehouse(createWarehouseDTO.name(), createWarehouseDTO.cep(), createWarehouseDTO.stock());
        return warehouse;
    }
}
