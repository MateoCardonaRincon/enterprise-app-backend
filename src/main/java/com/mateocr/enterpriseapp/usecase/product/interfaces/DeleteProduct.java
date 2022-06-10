package com.mateocr.enterpriseapp.usecase.product.interfaces;

import reactor.core.publisher.Mono;

@FunctionalInterface
public interface DeleteProduct {
    Mono<Void> apply(String productId);
}
