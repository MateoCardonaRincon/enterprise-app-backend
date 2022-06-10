package com.mateocr.enterpriseapp.usecase.product.interfaces;

import com.mateocr.enterpriseapp.dto.ProductDTO;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface CreateProduct {
    Mono<ProductDTO> apply(ProductDTO productDTO);
}
