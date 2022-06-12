package com.mateocr.enterpriseapp.repository;

import com.mateocr.enterpriseapp.collections.Supplier;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends ReactiveMongoRepository<Supplier, String> {

}
