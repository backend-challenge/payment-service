package com.invillia.acme.model.dto;

import com.invillia.acme.model.db.Item;
import com.invillia.acme.model.db.Order;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class OrderDto implements BaseDto<Order> {

    private String address;
    private LocalDate confirmationDate;
    private Integer status;
    private List<Item> items;

    @Override
    public Order getEntity() {
        return Order
                .builder()
                .address(this.getAddress())
                .items(this.getItems())
                .build();
    }
}
