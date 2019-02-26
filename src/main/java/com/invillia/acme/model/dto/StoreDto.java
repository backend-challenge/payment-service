package com.invillia.acme.model.dto;

import com.invillia.acme.model.db.Store;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreDto implements BaseDto<Store> {

    private String address;
    private String name;

    @Override
    public Store getEntity() {
        return Store
                .builder()
                .name(this.getName())
                .address(this.getAddress()).build();
    }
}
