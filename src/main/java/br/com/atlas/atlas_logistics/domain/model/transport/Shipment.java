package br.com.atlas.atlas_logistics.domain.model.transport;

import br.com.atlas.atlas_logistics.domain.model.Warehouse;

public class Shipment {

    private Long id;
    private Warehouse origin;
    private Warehouse destiny;
    private TransportStatus status;

}
