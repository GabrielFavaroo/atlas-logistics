package br.com.atlas.atlas_logistics.domain.model.documentModels.items;


import br.com.atlas.atlas_logistics.domain.model.relationalModels.items.Stock;
import org.springframework.data.elasticsearch.annotations.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Document(indexName = "product")
@Setting(shards = 2, replicas = 2)
public class ProductDocument{

    @MultiField(mainField = @Field(type = FieldType.Text),otherFields = @InnerField(suffix = "", type = FieldType.Keyword))
    private UUID id;

    @MultiField(mainField = @Field(type = FieldType.Text),otherFields = @InnerField(suffix = "", type = FieldType.Keyword))
    private String name;

    @MultiField(mainField = @Field(type = FieldType.Text),otherFields = @InnerField(suffix = "", type = FieldType.Keyword))
     private String sku;

    @Field(type = FieldType.Float)
     private BigDecimal value;

    @Field(type = FieldType.Nested)
    private Set<Stock> stock = new HashSet<>();

    public ProductDocument(String name , String sku , BigDecimal value) {
        this.value = value;
        this.sku = sku;
        this.name = name;
    }
}
