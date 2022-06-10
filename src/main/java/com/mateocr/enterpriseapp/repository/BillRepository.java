package com.mateocr.enterpriseapp.repository;

import com.mateocr.enterpriseapp.collections.Bill;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends ReactiveCrudRepository<Bill, String> {
}
