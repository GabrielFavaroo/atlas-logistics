package br.com.atlas.atlas_logistics.infrastructure.persistence.elastic.items;


import br.com.atlas.atlas_logistics.infrastructure.persistence.jpa.items.StockEntity;
import org.springframework.data.elasticsearch.annotations.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Document(indexName = "warehouse")
@Setting(shards = 2,replicas = 2,settingPath = "infrastructure/config/settings.json")


public class WarehouseDocument implements Serializable {

    @Field(type = FieldType.Keyword)
    private UUID id;

    @MultiField(mainField = @Field(type = FieldType.Text,analyzer = "logistics_analyzer"),
            otherFields = @InnerField(suffix = "raw", type = FieldType.Keyword,normalizer = "lowercase"))
    private String name;

    @MultiField(mainField = @Field(type = FieldType.Text,analyzer = "autocomplete_analyzer"),
            otherFields = @InnerField(suffix = "raw", type = FieldType.Keyword))
    private String cep;


    @Field(type = FieldType.Nested)
    private Set<StockDocument> stock = new HashSet<>();


    public WarehouseDocument(String name, String cep, Set<StockDocument> stock) {
        this.cep = cep;
        this.name = name;
        this.stock = stock;
    }

//    Sudeste	Cajamar,                    SP	07750-000  CD CAJ
//    Sul	Itajaí,                         SC	88301-001  CD IT
//    Nordeste	Cabo de Santo Agostinho,    PE	54505-000  CD SA
//    Centro-Oeste	Aparecida de Goiânia,   GO	74993-000  CD APGO
//    Norte	Manaus,                         AM	69075-010  CD MA



}
