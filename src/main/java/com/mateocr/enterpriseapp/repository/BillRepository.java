package com.mateocr.enterpriseapp.repository;

import com.mateocr.enterpriseapp.collections.Bill;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends ReactiveMongoRepository<Bill, String> {
}
