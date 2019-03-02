package com.invillia.acme.business;

import com.invillia.acme.model.db.Order;
import com.invillia.acme.model.db.Payment;
import com.invillia.acme.model.dto.OrderStatus;
import com.invillia.acme.model.dto.PaymentDto;
import com.invillia.acme.model.dto.PaymentStatus;
import com.invillia.acme.repository.OrderRepository;
import com.invillia.acme.repository.PaymentRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class PaymentBusinessTest {

	private static final String NUMBER_CREDIT_CARD = "1234-1234-1234-1234";
	private static final OrderStatus ORDER_STATUS = OrderStatus.APPROVED;
	private static final PaymentStatus PAYMENT_STATUS = PaymentStatus.APPROVED;

	@InjectMocks
	private PaymentBusiness business;

	@Mock
	private PaymentRepository repository;

	@Mock
	private OrderRepository orderRepository;

	private static final Integer ID = 1;

	@Test
	public void testMakePayment() {
		Mockito.when(orderRepository.findById(ID))
				.thenReturn(Optional.of(Order.builder().confirmationDate(LocalDate.now()).build()));
		business.makePayment(ID, this.getPaymentDto());
		ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.forClass(Order.class);
		ArgumentCaptor<Payment> paymentArgumentCaptor = ArgumentCaptor.forClass(Payment.class);
		Mockito.verify(repository).save(paymentArgumentCaptor.capture());
		Mockito.verify(orderRepository).save(orderArgumentCaptor.capture());

		Payment paymentResult = paymentArgumentCaptor.getValue();
		Order orderResult = orderArgumentCaptor.getValue();

		Assert.assertEquals(NUMBER_CREDIT_CARD, paymentResult.getNumberCreditCard());
		Assert.assertEquals(PAYMENT_STATUS, paymentResult.getStatus());
		Assert.assertEquals(ORDER_STATUS, orderResult.getStatus());
	}

	@Test(expected = HttpClientErrorException.class)
	public void testMakePaymentNotFound() {
		Mockito.when(orderRepository.findById(ID)).thenReturn(Optional.empty());
		business.makePayment(ID, this.getPaymentDto());
	}

	@Test(expected = RuntimeException.class)
	public void testMakePaymentError() {
		Mockito.when(orderRepository.findById(ID)).thenReturn(Optional.of(Order.builder().build()));
		business.makePayment(ID, this.getPaymentDto());
	}

	@Test(expected = HttpClientErrorException.class)
	public void testMakePaymentErroDataIntegrityViolation() {
		Mockito.when(orderRepository.findById(ID))
				.thenReturn(Optional.of(Order.builder().confirmationDate(LocalDate.now()).build()));
		Mockito.when(repository.save(Mockito.any(Payment.class))).thenThrow(DataIntegrityViolationException.class);
		business.makePayment(ID, this.getPaymentDto());
	}

	private PaymentDto getPaymentDto() {
		PaymentDto dto = new PaymentDto();
		dto.setNumberCreditCard(NUMBER_CREDIT_CARD);
		return dto;
	}
}
