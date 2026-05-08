package br.com.atlas.atlas_logistics.infrastructure.persistence.jpa.items;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Getter
@Setter
@Entity
@Table(name="warehouse")
@NoArgsConstructor

public class WarehouseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false,unique = true)
    private String name;

    @Column(nullable = false)
    private String cep;



    public WarehouseEntity(String name, String cep, Set<StockEntity> stockEntity) {
        this.cep = cep;
        this.name = name;
        this.stockEntity = stockEntity;
    }

    @OneToMany(mappedBy = "warehouse",fetch = FetchType.LAZY)
    private Set<StockEntity> stockEntity = new HashSet<>();

//    Sudeste	Cajamar,                    SP	07750-000  CD CAJ
//    Sul	Itajaí,                         SC	88301-001  CD IT
//    Nordeste	Cabo de Santo Agostinho,    PE	54505-000  CD SA
//    Centro-Oeste	Aparecida de Goiânia,   GO	74993-000  CD APGO
//    Norte	Manaus,                         AM	69075-010  CD MA



}
