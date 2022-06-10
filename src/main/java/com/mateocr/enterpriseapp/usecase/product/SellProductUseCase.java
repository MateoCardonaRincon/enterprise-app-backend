package com.mateocr.enterpriseapp.usecase.product;

import com.mateocr.enterpriseapp.dto.ProductDTO;
import com.mateocr.enterpriseapp.mapper.ProductMapper;
import com.mateocr.enterpriseapp.repository.ProductRepository;
import com.mateocr.enterpriseapp.usecase.product.interfaces.UpdateProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SellProductUseCase implements UpdateProduct {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public Mono<ProductDTO> apply(String id, Integer units) {
        return productRepository.findById(id).flatMap(product -> {
            product.setStock(product.getStock() - units);
            return productRepository.save(product)
                    .map(updatedProduct -> productMapper.convertCollectionToProductDTO()
                            .apply(updatedProduct));
        }).switchIfEmpty(Mono.just(new ProductDTO()));
    }
}
