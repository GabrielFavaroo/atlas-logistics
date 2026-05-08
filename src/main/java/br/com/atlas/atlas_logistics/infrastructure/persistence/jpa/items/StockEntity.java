package br.com.atlas.atlas_logistics.infrastructure.persistence.jpa.items;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "stockEntity")
public class StockEntity implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    @ManyToOne(optional = false)
    @JoinColumn(name = "warehouse_id")
    private WarehouseEntity warehouseEntity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;

    @Column(nullable = false)
    private int quantity;




}
