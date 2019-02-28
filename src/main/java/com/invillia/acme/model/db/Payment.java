package com.invillia.acme.model.db;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column
    private Integer status;
    @Column
    private String numberCreditCard;
    @Column
    private LocalDate paymentDate;
    @OneToOne()
    @JoinColumn(name = "id", unique = true)
    private Order orderE;

}
