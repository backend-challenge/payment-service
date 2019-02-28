package com.invillia.acme.model.db;

import com.invillia.acme.model.dto.OrderStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column
    private String address;
    @Column
    private LocalDate confirmationDate;
    @Column
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @OneToMany(cascade = {CascadeType.PERSIST})
    private List<Item> items;
}
