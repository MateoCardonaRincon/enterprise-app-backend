package com.mateocr.enterpriseapp.usecase.product;

import com.mateocr.enterpriseapp.repository.ProductRepository;
import com.mateocr.enterpriseapp.usecase.product.interfaces.DeleteProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DeleteProductUseCase implements DeleteProduct {

    private final ProductRepository productRepository;

    @Override
    public Mono<Void> apply(String supplierId) {
        return productRepository.deleteById(supplierId);
    }
}
