package br.com.atlas.atlas_logistics.domain.model.relationalModels.transport;

import br.com.atlas.atlas_logistics.domain.model.relationalModels.items.Warehouse;

public class Shipment {

    private Long id;
    private Warehouse origin;
    private Warehouse destiny;
    private TransportStatus status;

}
