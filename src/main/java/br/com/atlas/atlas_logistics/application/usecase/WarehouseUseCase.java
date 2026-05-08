package br.com.atlas.atlas_logistics.application.usecase;

import br.com.atlas.atlas_logistics.infrastructure.persistence.jpa.items.WarehouseEntity;
import br.com.atlas.atlas_logistics.infrastructure.persistence.jpa.repositories.WarehouseRepository;
import br.com.atlas.atlas_logistics.api.dtos.request.warehouse.CreateWarehouseDTO;
import br.com.atlas.atlas_logistics.application.mappers.WarehouseMapper;
import br.com.atlas.atlas_logistics.domain.exception.BusinessException;
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

        WarehouseEntity warehouseEntity = warehouseMapper.toCreateWarehouse(createWarehouseDTO);

        if(warehouseRepository.existsByName(warehouseEntity.getName())){
            throw new BusinessException("O armazém ja existe na base de dados");

        }
        else {
            warehouseRepository.save(warehouseEntity);
        }


    }

    public void deleteWarehouse(UUID id) {

        WarehouseEntity warehouseEntity = findWarehouseById(id);

       if(!warehouseEntity.getStockEntity().isEmpty()){
           throw new BusinessException("Não é possível deletar um armazém contendo items em seu estoque");
       }
       else {
           warehouseRepository.delete(warehouseEntity);
       }

    }

    private WarehouseEntity findWarehouseById(UUID id) {
        return warehouseRepository.findById(id).orElseThrow(() ->new BusinessException("Armazem não encontrado na base de dados")) ;
    }
}
