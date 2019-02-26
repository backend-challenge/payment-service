package com.invillia.acme.model.filter;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OrderFilter {

    private String address;
    private LocalDate confirmationDate;
    private Integer status;
}
