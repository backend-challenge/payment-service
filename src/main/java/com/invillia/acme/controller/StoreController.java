package com.invillia.acme.controller;

import com.invillia.acme.model.db.Store;
import com.invillia.acme.model.dto.StoreDto;
import com.invillia.acme.model.filter.StoreFilter;
import com.invillia.acme.repository.StoreRepository;
import com.invillia.acme.repository.specification.StoreSpecification;
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
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private StoreRepository repository;

    @ApiOperation("")
    @RequestMapping(method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<Store> createStore(@RequestBody StoreDto dto) {
        return ResponseEntity.ok(repository.save(dto.getEntity()));
    }

    @ApiOperation("")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public ResponseEntity<Store> updateStore(@RequestBody StoreDto dto) {
        return ResponseEntity.ok(repository.save(dto.getEntity()));
    }

    @ApiOperation("")
    @RequestMapping(method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
    public ResponseEntity<List<Store>> getStoreByFilter(@RequestBody StoreFilter filter) {
        Specification<Store> storeSpecification = StoreSpecification.getFilter(filter);
        return ResponseEntity.ok(repository.findAll(storeSpecification));
    }

}
