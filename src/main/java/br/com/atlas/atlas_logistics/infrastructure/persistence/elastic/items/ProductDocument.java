package br.com.atlas.atlas_logistics.infrastructure.persistence.elastic.items;


import br.com.atlas.atlas_logistics.infrastructure.persistence.jpa.items.StockEntity;
import org.springframework.data.elasticsearch.annotations.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Document(indexName = "product")
@Setting(shards = 2,replicas = 2,settingPath = "infrastructure/config/settings.json")
public class ProductDocument{

    @Field(type = FieldType.Keyword)
    private UUID id;

    @MultiField(mainField = @Field(type = FieldType.Text,analyzer = "logistics_analyzer")
            ,otherFields = @InnerField(suffix = "raw", type = FieldType.Keyword,normalizer = "lowercase"))
    private String name;

    @MultiField(mainField = @Field(type = FieldType.Text,analyzer = "autocomplete_analyzer")
            ,otherFields = @InnerField(suffix = "raw", type = FieldType.Keyword,normalizer = "lowercase"))
     private String sku;

    @Field(type = FieldType.Double)
     private BigDecimal value;

    @Field(type = FieldType.Nested)
    private Set<StockDocument> stock= new HashSet<>();

    public ProductDocument(String name , String sku , BigDecimal value) {
        this.value = value;
        this.sku = sku;
        this.name = name;
    }
}
