package com.mateocr.enterpriseapp.usecase.product;

import com.mateocr.enterpriseapp.dto.ProductDTO;
import com.mateocr.enterpriseapp.mapper.ProductMapper;
import com.mateocr.enterpriseapp.repository.ProductRepository;
import com.mateocr.enterpriseapp.usecase.product.interfaces.RestockProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RestockProductUseCase implements RestockProduct {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public Mono<ProductDTO> apply(ProductDTO productDTO, Integer units) {
        return productRepository.findById(productDTO.getId())
                .filter(soldProduct -> soldProduct.getStock() + units <= soldProduct.getMaximumAmount())
                .flatMap(soldProduct -> {
                    soldProduct.setStock(soldProduct.getStock() + units);
                    return productRepository.save(soldProduct)
                            .map(updatedProduct -> productMapper.convertCollectionToProductDTO().apply(updatedProduct));
                })
                .switchIfEmpty(Mono.just(new ProductDTO()));
    }
}
