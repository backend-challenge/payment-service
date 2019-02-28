package com.invillia.acme.controller;

import com.invillia.acme.model.db.Order;
import com.invillia.acme.model.db.Payment;
import com.invillia.acme.model.dto.PaymentDto;
import com.invillia.acme.repository.OrderRepository;
import com.invillia.acme.repository.PaymentRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentRepository repository;

    @Autowired
    private OrderRepository orderRepository;

    @ApiOperation("Realiza o pagamento de uma ordem")
    @RequestMapping(value = "/{id}", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<?> makePaymentOrder(@PathVariable("id") Integer id, @RequestBody PaymentDto dto) {

        Order orderEntity = orderRepository.findById(id).orElseThrow(
                () -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Ordem não encontrada")
        );

        if (Objects.isNull(orderEntity.getConfirmationDate())) {
            throw new RuntimeException("Ordem não confirmada");
        }

        Payment paymentEntity = new Payment();
        BeanUtils.copyProperties(dto, paymentEntity, "id", "order");
        paymentEntity.setOrderE(orderEntity);
        Optional.ofNullable(repository.save(paymentEntity))
                .orElseThrow(PersistenceException::new);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation("Cria a solicitação de reembolso de uma ordem de compra")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
    public ResponseEntity<List<Order>> refundedOrder(@RequestParam(value = "id") Integer paymentId) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation("Cria a solicitação de estorno de um item de uma determinada ordem de compra")
    @RequestMapping(value = "/{id}/{itemId}", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<List<Order>> refundedItem(@RequestParam(value = "id") Integer paymentId, @RequestParam(value = "itemId") Integer itemId) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
