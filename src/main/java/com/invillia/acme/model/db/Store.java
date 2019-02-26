package com.invillia.acme.model.db;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@Builder
public class Store {

    @Id
    private Integer id;
    @Column
    private String address;
    @Column
    private String name;

}
