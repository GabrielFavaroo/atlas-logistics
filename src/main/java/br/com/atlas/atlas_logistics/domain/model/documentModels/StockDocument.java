package br.com.atlas.atlas_logistics.domain.model.documentModels;


import br.com.atlas.atlas_logistics.domain.model.relationalModels.items.Product;
import br.com.atlas.atlas_logistics.domain.model.relationalModels.items.Warehouse;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.util.UUID;

@Document(indexName = "stock")
@Setting(shards = 2,replicas = 2)
public class StockDocument{


    private UUID id;



    private Warehouse warehouse;


    private Product product;


    private int quantity;




}
