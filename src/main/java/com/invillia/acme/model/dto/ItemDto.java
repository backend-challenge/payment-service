package com.invillia.acme.model.dto;

import com.invillia.acme.model.db.Item;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemDto implements BaseDto<Item> {

    private String description;
    private BigDecimal unitPrice;
    private Integer quantity;

    @Override
    public Item getEntity() {
        return Item
                .builder()
                .description(this.getDescription())
                .quantity(this.getQuantity())
                .unitPrice(this.getUnitPrice())
                .build();
    }
}
