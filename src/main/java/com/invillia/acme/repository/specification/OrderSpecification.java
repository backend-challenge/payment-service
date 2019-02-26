package com.invillia.acme.repository.specification;

import com.invillia.acme.model.db.Order;
import com.invillia.acme.model.filter.OrderFilter;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Objects;

public class OrderSpecification {

    public static Specification<Order> getFilter(OrderFilter filter) {
        return Specification
                .where(likeAddress(filter.getAddress())
                        .and(equalConfirmationDate(filter.getConfirmationDate())
                                .and(equalStatus(filter.getStatus()))));
    }

    private static Specification<Order> likeAddress(String address) {
        return (root, query, cb) -> {
            if (Objects.nonNull(address)) {
                return cb.like(cb.upper(root.get("name")), "%" + address.toUpperCase() + "%");
            }
            return null;
        };
    }

    private static Specification<Order> equalStatus(Integer status) {
        return (root, query, cb) -> {
            if (Objects.nonNull(status)) {
                return cb.equal(root.get("status"), status);
            }
            return null;
        };
    }

    private static Specification<Order> equalConfirmationDate(LocalDate confirmationDate) {
        return (root, query, cb) -> {
            if (Objects.nonNull(confirmationDate)) {
                return cb.equal(root.get("confirmationDate"), confirmationDate);
            }
            return null;
        };
    }
}
