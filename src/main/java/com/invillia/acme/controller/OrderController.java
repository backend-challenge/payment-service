package com.invillia.acme.controller;

import com.invillia.acme.model.db.Order;
import com.invillia.acme.model.dto.OrderDto;
import com.invillia.acme.model.filter.OrderFilter;
import com.invillia.acme.repository.OrderRepository;
import com.invillia.acme.repository.specification.OrderSpecification;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderRepository repository;

    @ApiOperation("Cria uma nova ordem com os itens")
    @RequestMapping(method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<Order> createStore(@RequestBody OrderDto dto) {
        return ResponseEntity.ok(repository.save(dto.getEntity()));
    }

    @ApiOperation("Cria uma nova ordem com os itens")
    @RequestMapping(method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<List<Order>> getOrderByFilter(@RequestBody OrderFilter filter) {
        Specification<Order> orderSpecification = OrderSpecification.getFilter(filter);
        return ResponseEntity.ok(repository.findAll(orderSpecification));
    }

}
