package br.com.atlas.atlas_logistics.application.usecase;

import br.com.atlas.atlas_logistics.adapters.persistence.WarehouseRepository;
import br.com.atlas.atlas_logistics.adapters.web.dtos.request.warehouse.CreateWarehouseDTO;
import br.com.atlas.atlas_logistics.application.mappers.WarehouseMapper;
import br.com.atlas.atlas_logistics.domain.exception.BusinessException;
import br.com.atlas.atlas_logistics.domain.model.relationalModels.items.Warehouse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WarehouseUseCase {
    private final WarehouseRepository warehouseRepository;
    private final WarehouseMapper warehouseMapper;

    public WarehouseUseCase(WarehouseRepository warehouseRepository, WarehouseMapper warehouseMapper) {
        this.warehouseRepository = warehouseRepository;
        this.warehouseMapper = warehouseMapper;
    }

    public void createWarehouse(CreateWarehouseDTO createWarehouseDTO){

        Warehouse warehouse = warehouseMapper.toCreateWarehouse(createWarehouseDTO);

        if(warehouseRepository.existsByName(warehouse.getName())){
            throw new BusinessException("O armazém ja existe na base de dados");

        }
        else {
            warehouseRepository.save(warehouse);
        }


    }

    public void deleteWarehouse(UUID id) {

        Warehouse warehouse = findWarehouseById(id);

       if(!warehouse.getStock().isEmpty()){
           throw new BusinessException("Não é possível deletar um armazém contendo items em seu estoque");
       }
       else {
           warehouseRepository.delete(warehouse);
       }

    }

    private Warehouse findWarehouseById(UUID id) {
        return warehouseRepository.findById(id).orElseThrow(() ->new BusinessException("Armazem não encontrado na base de dados")) ;
    }
}
