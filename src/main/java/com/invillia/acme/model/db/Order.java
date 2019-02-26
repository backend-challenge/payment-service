package com.invillia.acme.model.db;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
public class Order {

    @Id
    private Integer id;
    @Column
    private String address;
    @Column
    private LocalDate confirmationDate;
    @Column
    private Integer status;
    @ManyToOne
    private List<Item> items;

}
