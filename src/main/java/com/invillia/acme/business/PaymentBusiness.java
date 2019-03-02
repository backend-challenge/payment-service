package com.invillia.acme.business;

import com.invillia.acme.model.db.Order;
import com.invillia.acme.model.db.Payment;
import com.invillia.acme.model.dto.OrderStatus;
import com.invillia.acme.model.dto.PaymentDto;
import com.invillia.acme.model.dto.PaymentStatus;
import com.invillia.acme.repository.OrderRepository;
import com.invillia.acme.repository.PaymentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Component
public class PaymentBusiness {

	@Autowired
	private PaymentRepository repository;

	@Autowired
	private OrderRepository orderRepository;

	public void makePayment(Integer id, PaymentDto dto) {
		Order orderEntity = orderRepository.findById(id).orElseThrow(
				() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Ordem não encontrada")
		);

		orderEntity.setStatus(OrderStatus.APPROVED);

		if (Objects.isNull(orderEntity.getConfirmationDate())) {
			throw new RuntimeException("Ordem não confirmada");
		}

		Payment paymentEntity = new Payment();
		BeanUtils.copyProperties(dto, paymentEntity, "id", "order");
		paymentEntity.setOrderE(orderEntity);
		paymentEntity.setPaymentDate(LocalDate.now());
		try {
			paymentEntity.setStatus(PaymentStatus.APPROVED);
			repository.save(paymentEntity);
			orderRepository.save(orderEntity);
		} catch (DataIntegrityViolationException e) {
			throw new HttpClientErrorException(HttpStatus.CONFLICT, "Pagamento já realizado");
		}
	}

	public List<Payment> listPayments() {
		return repository.findAll();
	}
}
