package com.invillia.acme.model.db;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
public class Item {

    @Id
    private Integer id;
    @Column
    private String description;
    @Column
    private BigDecimal unitPrice;
    @Column
    private Integer quantity;
}
