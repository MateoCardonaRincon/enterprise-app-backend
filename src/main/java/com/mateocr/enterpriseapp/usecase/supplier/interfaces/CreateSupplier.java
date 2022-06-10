package com.mateocr.enterpriseapp.usecase.supplier.interfaces;

import com.mateocr.enterpriseapp.dto.SupplierDTO;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface CreateSupplier {
    Mono<SupplierDTO> apply(SupplierDTO resourceDTO);
}
