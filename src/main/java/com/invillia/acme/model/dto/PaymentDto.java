package com.invillia.acme.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PaymentDto {

    private Integer status;
    private String numberCreditCard;
    private LocalDate paymentDate;

}
