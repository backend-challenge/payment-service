package com.invillia.acme.model.dto;

import lombok.Getter;

@Getter
public enum OrderStatus {

	PENDING_PAYMENT,
	APPROVED,
	REFUNDED,
	REFUND_REQUEST
}
