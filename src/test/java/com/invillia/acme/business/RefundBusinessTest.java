package com.invillia.acme.business;

import com.invillia.acme.model.db.Item;
import com.invillia.acme.model.db.Order;
import com.invillia.acme.model.db.Payment;
import com.invillia.acme.model.dto.OrderStatus;
import com.invillia.acme.model.dto.PaymentStatus;
import com.invillia.acme.repository.ItemRepository;
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
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class RefundBusinessTest {

	private static final Integer ID = 1;
	private static final PaymentStatus PAYMENT_STATUS = PaymentStatus.PENDING_REFUNDED;
	private static final OrderStatus ORDER_STATUS = OrderStatus.REFUND_REQUEST;
	private static final String NUMBER_CREDIT_CARD = "1234-1234-1234-1234";
	private static final String ADDRESS = "adress";

	@InjectMocks
	private RefundBusiness business;

	@Mock
	private PaymentRepository paymentRepository;

	@Mock
	private ItemRepository itemRepository;

	@Mock
	private OrderRepository orderRepository;

	@Test
	public void testRefundOrderById() {
		Mockito.when(paymentRepository.findByOrderE(Mockito.any(Order.class))).thenReturn(Payment.builder().build());
		business.refundOrderById(ID);
		ArgumentCaptor<Payment> argumentCaptor = ArgumentCaptor.forClass(Payment.class);
		Mockito.verify(paymentRepository).save(argumentCaptor.capture());
		Payment result = argumentCaptor.getValue();

		Assert.assertEquals(PAYMENT_STATUS, result.getStatus());
		Assert.assertNotNull(result.getPaymentDate());
	}

	@Test(expected = HttpClientErrorException.class)
	public void testRefundOrderByIdNotFound() {
		Mockito.when(paymentRepository.findByOrderE(Mockito.any(Order.class))).thenReturn(null);
		business.refundOrderById(ID);
	}

	@Test
	public void testRefundItem() {
		Mockito.when(paymentRepository.findByOrderE(Mockito.any(Order.class))).thenReturn(this.getPayment());
		Mockito.when(itemRepository.findById(ID)).thenReturn(Optional.of(new Item()));
		business.refundItem(ID, ID);
		ArgumentCaptor<Item> itemArgumentCaptor = ArgumentCaptor.forClass(Item.class);
		ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.forClass(Order.class);
		ArgumentCaptor<Payment> paymentArgumentCaptor = ArgumentCaptor.forClass(Payment.class);

		Mockito.verify(itemRepository).save(itemArgumentCaptor.capture());
		Mockito.verify(orderRepository).save(orderArgumentCaptor.capture());
		Mockito.verify(paymentRepository).save(paymentArgumentCaptor.capture());

		Item itemResult = itemArgumentCaptor.getValue();
		Order orderResult = orderArgumentCaptor.getValue();
		Payment paymentResult = paymentArgumentCaptor.getValue();

		Assert.assertNotNull(itemResult);
		Assert.assertEquals(ORDER_STATUS, orderResult.getStatus());
		Assert.assertEquals(PAYMENT_STATUS, paymentResult.getStatus());
	}

	private Payment getPayment() {
		return Payment.builder()
				.numberCreditCard(NUMBER_CREDIT_CARD)
				.orderE(
						Order.builder()
								.confirmationDate(LocalDate.now())
								.address(ADDRESS)
								.items(
										Collections.singletonList(Item.builder()
												.id(ID)
												.build())
								)
								.build()
				).build();
	}
}
