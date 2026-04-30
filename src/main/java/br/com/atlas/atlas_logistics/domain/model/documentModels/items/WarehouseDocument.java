package br.com.atlas.atlas_logistics.domain.model.documentModels.items;


import br.com.atlas.atlas_logistics.domain.model.relationalModels.items.Stock;
import org.springframework.data.elasticsearch.annotations.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Document(indexName = "warehouse")
@Setting(shards = 2,replicas = 2)
public class WarehouseDocument implements Serializable {

    @Field(type = FieldType.Keyword)
    private UUID id;

    @MultiField(mainField = @Field(type = FieldType.Text),otherFields = @InnerField(suffix = "", type = FieldType.Keyword))
    private String name;

    @MultiField(mainField = @Field(type = FieldType.Text),otherFields = @InnerField(suffix = "", type = FieldType.Keyword))
    private String cep;


    @Field(type = FieldType.Nested)
    private Set<Stock> stock = new HashSet<>();


    public WarehouseDocument(String name, String cep, Set<Stock> stock) {
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
