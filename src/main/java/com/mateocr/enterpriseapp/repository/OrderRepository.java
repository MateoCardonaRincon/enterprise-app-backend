package com.mateocr.enterpriseapp.repository;

import com.mateocr.enterpriseapp.collections.Order;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends ReactiveCrudRepository<Order, String> {
}
