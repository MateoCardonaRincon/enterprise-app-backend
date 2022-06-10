package com.mateocr.enterpriseapp.usecase.product;

import com.mateocr.enterpriseapp.dto.ProductDTO;
import com.mateocr.enterpriseapp.mapper.ProductMapper;
import com.mateocr.enterpriseapp.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class GetAllProductsUseCase implements Supplier<Flux<ProductDTO>> {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public Flux<ProductDTO> get() {
        return productRepository.findAll()
                .map(product -> productMapper.convertCollectionToProductDTO().apply(product));
    }
}
