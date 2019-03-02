package com.invillia.acme.business;

import com.invillia.acme.model.db.Item;
import com.invillia.acme.model.db.Order;
import com.invillia.acme.model.db.Payment;
import com.invillia.acme.model.dto.OrderStatus;
import com.invillia.acme.model.dto.PaymentStatus;
import com.invillia.acme.repository.ItemRepository;
import com.invillia.acme.repository.OrderRepository;
import com.invillia.acme.repository.PaymentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class RefundBusiness {

	@Autowired
	private PaymentRepository repository;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private OrderRepository orderRepository;

	public Payment refundOrderById(Integer orderId) {
		Payment entity = this.getPaymentByOrder(orderId);
		entity.setStatus(PaymentStatus.PENDING_REFUNDED);
		entity.setPaymentDate(LocalDate.now());
		repository.save(entity);
		return entity;
	}

	@Transactional
	public void refundItem(Integer orderId, Integer itemId) {
		Payment paymentEntity = this.getPaymentByOrder(orderId);
		Order orderEntity = paymentEntity.getOrderE();
		List<Item> itemList = orderEntity.getItems();
		this.itemValidation(itemId, itemList);

		// Remove a ordem com o item que foi estornado
		Item itemRefund = itemRepository.findById(itemId).get();
		orderEntity.getItems().remove(itemRefund);
		// Cria um novo pagamento com uma nova ordem de compra com o status de pendente reembolso
		this.createPaymentToRefund(paymentEntity, orderEntity, itemRefund);

	}

	private Payment getPaymentByOrder(Integer orderId) {
		return Optional.ofNullable(repository.findByOrderE(Order.builder().id(orderId).build()))
				.orElseThrow(
						() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Ordem não encontrada")
				);
	}

	private void itemValidation(Integer itemId, List<Item> itemList) {
		Boolean hasItem = itemList.stream()
				.map(Item::getId)
				.anyMatch(x -> x.equals(itemId));

		if (!hasItem) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Item não encontrado");
		}
	}

	private void createPaymentToRefund(Payment payment, Order order, Item item) {

		Item refundItem = new Item();
		BeanUtils.copyProperties(item, refundItem, "id");
		itemRepository.save(item);

		Order refundOrder = new Order();
		BeanUtils.copyProperties(order, refundOrder, "id", "status", "items");
		refundOrder.setItems(Arrays.asList(item));
		refundOrder.setStatus(OrderStatus.REFUND_REQUEST);
		orderRepository.save(refundOrder);

		Payment refundPayment = new Payment();
		BeanUtils.copyProperties(payment, refundPayment, "id", "orderE");
		refundPayment.setStatus(PaymentStatus.PENDING_REFUNDED);
		refundPayment.setOrderE(refundOrder);
		repository.save(refundPayment);
	}


}
