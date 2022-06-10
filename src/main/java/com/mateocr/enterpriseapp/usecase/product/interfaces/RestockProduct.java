package com.mateocr.enterpriseapp.usecase.product.interfaces;

import com.mateocr.enterpriseapp.dto.ProductDTO;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface RestockProduct {
    Mono<ProductDTO> apply(ProductDTO product, Integer units);
}
