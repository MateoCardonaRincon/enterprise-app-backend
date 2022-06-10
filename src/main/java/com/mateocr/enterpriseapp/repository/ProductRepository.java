package com.mateocr.enterpriseapp.repository;

import com.mateocr.enterpriseapp.collections.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, String> {
}
