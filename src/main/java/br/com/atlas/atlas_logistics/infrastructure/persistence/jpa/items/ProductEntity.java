package br.com.atlas.atlas_logistics.infrastructure.persistence.jpa.items;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Entity
@Table(name= "product")
@Getter
@Setter
@NoArgsConstructor

public class ProductEntity implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)

    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,unique = true)
     private String sku;

    @Column(nullable = false)
     private BigDecimal value;


    @OneToMany(mappedBy = "product",fetch = FetchType.LAZY)

    private Set<StockEntity> stockEntity = new HashSet<>();

    public ProductEntity(String name , String sku , BigDecimal value) {
        this.value = value;
        this.sku = sku;
        this.name = name;
    }
}
