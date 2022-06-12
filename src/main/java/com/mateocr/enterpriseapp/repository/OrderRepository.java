package com.mateocr.enterpriseapp.repository;

import com.mateocr.enterpriseapp.collections.Order;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends ReactiveMongoRepository<Order, String> {
}
