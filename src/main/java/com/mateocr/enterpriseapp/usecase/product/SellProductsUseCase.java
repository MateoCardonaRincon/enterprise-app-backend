package com.mateocr.enterpriseapp.usecase.product;

import com.mateocr.enterpriseapp.dto.BillDTO;
import com.mateocr.enterpriseapp.dto.ProductDTO;
import com.mateocr.enterpriseapp.mapper.ProductMapper;
import com.mateocr.enterpriseapp.repository.ProductRepository;
import com.mateocr.enterpriseapp.usecase.product.interfaces.SellProducts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class SellProductsUseCase implements SellProducts {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public Flux<ProductDTO> apply(BillDTO billDTO) {
        return Flux.fromIterable(billDTO.getSoldProducts())
                .flatMap(Mono::just)
                .filter(productDTO -> productDTO.getStock() - productDTO.getSoldUnits() >= 0)
                .flatMap(productDTO -> {
                    productDTO.setStock(productDTO.getStock() - productDTO.getSoldUnits());
                    return productRepository.save(productMapper.convertProductDTOToCollection().apply(productDTO))
                            .map(updatedProduct -> productMapper.convertCollectionToProductDTO().apply(updatedProduct));
                }).switchIfEmpty(Flux.empty());
    }
}
