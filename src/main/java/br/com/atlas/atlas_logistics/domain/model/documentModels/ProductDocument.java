package br.com.atlas.atlas_logistics.domain.model.documentModels;


import br.com.atlas.atlas_logistics.domain.model.relationalModels.items.Stock;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Document(indexName = "product")
@Setting(shards = 2, replicas = 2)
public class ProductDocument{

    private UUID id;


    private String name;


     private String sku;


     private BigDecimal value;


    private Set<Stock> stock = new HashSet<>();

    public ProductDocument(String name , String sku , BigDecimal value) {
        this.value = value;
        this.sku = sku;
        this.name = name;
    }
}
