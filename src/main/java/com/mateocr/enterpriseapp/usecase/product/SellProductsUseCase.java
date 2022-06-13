package com.mateocr.enterpriseapp.usecase.product;

import com.mateocr.enterpriseapp.dto.BillDTO;
import com.mateocr.enterpriseapp.dto.ProductDTO;
import com.mateocr.enterpriseapp.mapper.ProductMapper;
import com.mateocr.enterpriseapp.repository.ProductRepository;
import com.mateocr.enterpriseapp.usecase.product.interfaces.SellProducts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;


@Service
@Validated
@RequiredArgsConstructor
public class SellProductsUseCase implements SellProducts {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public Flux<ProductDTO> apply(@Valid BillDTO billDTO) {
        return Flux.fromIterable(billDTO.getSoldProducts())
                .flatMap(Mono::just)
                .filter(product -> product.getStock() - product.getSoldUnits() >= 0)
                .flatMap(product -> {
                    product.setStock(product.getStock() - product.getSoldUnits());
                    return productRepository.save(product)
                            .map(updatedProduct -> productMapper.convertCollectionToProductDTO().apply(updatedProduct));
                }).switchIfEmpty(Flux.empty());
    }
}
