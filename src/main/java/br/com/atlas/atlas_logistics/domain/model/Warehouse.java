package br.com.atlas.atlas_logistics.domain.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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

public class Warehouse implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false,unique = true)
    private String name;

    @Column(nullable = false)
    private String cep;



    public Warehouse(String name,String cep,Set<Stock> stock) {
        this.cep = cep;
        this.name = name;
        this.stock = stock;
    }

    @OneToMany(mappedBy = "warehouse",fetch = FetchType.LAZY)
    private Set<Stock> stock = new HashSet<>();

//    Sudeste	Cajamar,                    SP	07750-000  CD CAJ
//    Sul	Itajaí,                         SC	88301-001  CD IT
//    Nordeste	Cabo de Santo Agostinho,    PE	54505-000  CD SA
//    Centro-Oeste	Aparecida de Goiânia,   GO	74993-000  CD APGO
//    Norte	Manaus,                         AM	69075-010  CD MA



}
