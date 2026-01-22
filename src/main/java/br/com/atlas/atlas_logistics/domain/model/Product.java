package br.com.atlas.atlas_logistics.domain.model;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
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

public class Product implements Serializable {
    private static final long SerialVersionUID= 1L;

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

    private Set<Stock> stock = new HashSet<>();

    public Product( String name , String sku ,BigDecimal value) {
        this.value = value;
        this.sku = sku;
        this.name = name;
    }
}
