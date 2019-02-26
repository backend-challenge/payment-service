package com.invillia.acme.repository.specification;

import com.invillia.acme.model.db.Store;
import com.invillia.acme.model.filter.StoreFilter;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;

public class StoreSpecification {

    public static Specification<Store> getFilter(StoreFilter filter) {
        return Specification
                .where(likeAddress(filter.getAddress())
                        .and(likeName(filter.getName())));
    }

    private static Specification<Store> likeAddress(String address) {
        return (root, query, cb) -> {
            if (Objects.nonNull(address)) {
                return cb.like(cb.upper(root.get("name")), "%" + address.toUpperCase() + "%");
            }
            return null;
        };
    }

    private static Specification<Store> likeName(String name) {
        return (root, query, cb) -> {
            if (Objects.nonNull(name)) {
                return cb.like(cb.upper(root.get("name")), "%" + name.toUpperCase() + "%");
            }
            return null;
        };
    }
}
