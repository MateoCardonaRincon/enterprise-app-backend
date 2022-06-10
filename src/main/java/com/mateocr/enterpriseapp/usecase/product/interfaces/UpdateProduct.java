package com.mateocr.enterpriseapp.usecase.product.interfaces;

import com.mateocr.enterpriseapp.dto.ProductDTO;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface UpdateProduct {
    Mono<ProductDTO> apply(String productId, Integer units);
}
