package br.com.atlas.atlas_logistics.infrastructure.persistence.elastic.items;


import br.com.atlas.atlas_logistics.infrastructure.persistence.jpa.items.ProductEntity;
import br.com.atlas.atlas_logistics.infrastructure.persistence.jpa.items.WarehouseEntity;

import org.springframework.data.elasticsearch.annotations.*;

import java.util.UUID;

@Document(indexName = "stockEntity")
@Setting(shards = 2,replicas = 2,settingPath = "infrastructure/config/settings.json")

public class StockDocument{

    @Field(type = FieldType.Keyword)
    private UUID id;

    @Field(type = FieldType.Object)
    private WarehouseDocument warehouse;

    @Field(type = FieldType.Object)
    private ProductDocument product;

    @Field(type = FieldType.Integer)
    private int quantity;




}
