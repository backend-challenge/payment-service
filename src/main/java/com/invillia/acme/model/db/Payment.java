package com.invillia.acme.model.db;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
public class Payment {

    @Id
    private Integer id;
    @Column
    private Integer status;
    @Column
    private String numberCreditCard;
    @Column
    private LocalDate paymentDate;
    @OneToOne
    private Order order;

}
