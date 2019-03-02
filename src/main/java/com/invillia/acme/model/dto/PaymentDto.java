package com.invillia.acme.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

@Getter
@Setter
public class PaymentDto {

	@NumberFormat(pattern = "####-####-####-####")
	private String numberCreditCard;

}
