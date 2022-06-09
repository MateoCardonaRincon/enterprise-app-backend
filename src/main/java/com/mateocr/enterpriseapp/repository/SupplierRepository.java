package com.mateocr.enterpriseapp.repository;

import com.mateocr.enterpriseapp.collections.Supplier;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends ReactiveCrudRepository<Supplier, String> {

}
