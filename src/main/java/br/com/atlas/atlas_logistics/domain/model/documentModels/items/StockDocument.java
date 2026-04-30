package br.com.atlas.atlas_logistics.domain.model.documentModels.items;


import br.com.atlas.atlas_logistics.domain.model.relationalModels.items.Product;
import br.com.atlas.atlas_logistics.domain.model.relationalModels.items.Warehouse;

import org.springframework.data.elasticsearch.annotations.*;

import java.util.UUID;

@Document(indexName = "stock")
@Setting(shards = 2,replicas = 2)
public class StockDocument{

    @MultiField(mainField = @Field(type = FieldType.Text),otherFields = @InnerField(suffix = "", type = FieldType.Keyword))
    private UUID id;

    @Field(type = FieldType.Object)
    private Warehouse warehouse;

    @Field(type = FieldType.Object)
    private Product product;

    @Field(type = FieldType.Integer)
    private int quantity;




}
