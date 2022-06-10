package com.mateocr.enterpriseapp.usecase.supplier.interfaces;

import reactor.core.publisher.Mono;

@FunctionalInterface
public interface DeleteSupplier {
    Mono<Void> apply(String supplierId);
}
