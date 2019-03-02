package com.invillia.acme.controller;

import com.invillia.acme.business.PaymentBusiness;
import com.invillia.acme.business.RefundBusiness;
import com.invillia.acme.model.db.Payment;
import com.invillia.acme.model.dto.PaymentDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	private PaymentBusiness paymentBusiness;

	@Autowired
	private RefundBusiness refundBusiness;

	@ApiOperation("Realiza o pagamento de uma ordem")
	@RequestMapping(value = "/{id}", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<?> makePaymentOrder(@PathVariable("id") Integer id, @RequestBody PaymentDto dto) {
		paymentBusiness.makePayment(id, dto);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@ApiOperation("Lista todos os pagamentos")
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Payment>> listPayments() {
		return ResponseEntity.ok(paymentBusiness.listPayments());
	}

	@ApiOperation("Cria a solicitação de reembolso de uma ordem de compra")
	@RequestMapping(value = "/refund/order/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> refundOrder(@PathVariable("id") Integer orderId) {
		refundBusiness.refundOrderById(orderId);
		return ResponseEntity.accepted().build();
	}

	@ApiOperation("Cria a solicitação de reembolso de um item de uma determinada ordem de compra")
	@RequestMapping(value = "/refund/order/{orderId}/item/{itemId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> refundedItem(@PathVariable(value = "orderId") Integer orderId, @PathVariable(value = "itemId") Integer itemId) {
		refundBusiness.refundItem(orderId, itemId);
		return ResponseEntity.accepted().build();
	}


}
