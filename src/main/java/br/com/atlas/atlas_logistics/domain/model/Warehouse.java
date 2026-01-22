package br.com.atlas.atlas_logistics.domain.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Getter
@Setter
@Entity
@Table(name="warehouse")
public class Warehouse implements Serializable {

    private static final long serialVersionUid = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false,unique = true)
    private String name;

    @Column(nullable = false)
    private String cep;

    @OneToMany(mappedBy = "warehouse",fetch = FetchType.LAZY)

    private Set<Stock> stock = new HashSet<>();




}
