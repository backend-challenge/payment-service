package com.invillia.acme.model.dto;

import com.invillia.acme.model.db.Order;
import com.invillia.acme.model.db.Payment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PaymentDto implements BaseDto<Payment> {

    private Integer status;
    private String numberCreditCard;
    private LocalDate paymentDate;
    private Order order;

    @Override
    public Payment getEntity() {
        return Payment
                .builder()
                .numberCreditCard(this.getNumberCreditCard())
                .order(this.getOrder())
                .status(this.getStatus())
                .paymentDate(LocalDate.now())
                .build();
    }
}
