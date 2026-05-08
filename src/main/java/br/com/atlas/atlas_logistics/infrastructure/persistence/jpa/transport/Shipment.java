package br.com.atlas.atlas_logistics.infrastructure.persistence.jpa.transport;

import br.com.atlas.atlas_logistics.infrastructure.persistence.jpa.items.WarehouseEntity;

public class Shipment {

    private Long id;
    private WarehouseEntity origin;
    private WarehouseEntity destiny;
    private TransportStatus status;

}
