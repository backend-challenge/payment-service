package com.invillia.acme.model.dto;

import lombok.Getter;

@Getter
public enum PaymentStatus {

	REFUNDED,
	PENDING_REFUNDED,
	APPROVED
}
