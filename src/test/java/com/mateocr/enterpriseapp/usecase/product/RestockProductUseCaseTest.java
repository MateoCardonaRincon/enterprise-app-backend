package com.mateocr.enterpriseapp.usecase.product;

import com.mateocr.enterpriseapp.collections.Product;
import com.mateocr.enterpriseapp.collections.Supplier;
import com.mateocr.enterpriseapp.dto.ProductDTO;
import com.mateocr.enterpriseapp.mapper.ProductMapper;
import com.mateocr.enterpriseapp.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;

@SpringBootTest
class RestockProductUseCaseTest {
    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper mapper;

    @SpyBean
    private RestockProductUseCase useCase;

    @Test
    @DisplayName("Update product")
    void updateProduct() {

        Supplier supplier = new Supplier();
        supplier.setId("123");
        supplier.setName("homecenter");
        supplier.setNotes("screws, hammer");
        supplier.setPhoneNumber("123456");
        supplier.setPersonalId("321654");

        Product product = new Product("123", "screw", "steel screw", 50, 20, 100, 150., supplier, 0);

        Product restockedProduct = new Product("123", "screw", "steel screw", 70, 20, 100, 150., supplier, 20);

        ProductDTO productDTO = mapper.convertCollectionToProductDTO().apply(product);

        Mockito.when(productRepository.findById(any(String.class))).thenReturn(Mono.just(product));

        Mockito.when(productRepository.save(any())).thenReturn(Mono.just(restockedProduct));

        var result = useCase.apply(productDTO, productDTO.getSoldUnits());

        Assertions.assertEquals(restockedProduct.getId(), result.block().getId());
        Assertions.assertEquals(restockedProduct.getName(), result.block().getName());
        Assertions.assertEquals(restockedProduct.getDescription(), result.block().getDescription());
        Assertions.assertEquals(restockedProduct.getStock(), result.block().getStock());
        Assertions.assertEquals(restockedProduct.getPrice(), result.block().getPrice());
        Assertions.assertEquals(restockedProduct.getMaximumAmount(), result.block().getMaximumAmount());
        Assertions.assertEquals(restockedProduct.getMinimumAmount(), result.block().getMinimumAmount());
        Assertions.assertEquals(restockedProduct.getSoldUnits(), result.block().getSoldUnits());
        Assertions.assertEquals(restockedProduct.getSupplier(), result.block().getSupplier());

        StepVerifier
                .create(Mono.just(Mockito.when(useCase.apply(productDTO, productDTO.getSoldUnits()))
                        .thenReturn(Mono.just(productDTO))))
                .expectNextCount(1)
                .verifyComplete();

        Mockito.verify(productRepository).findById(product.getId());
        Mockito.verify(productRepository, atLeastOnce()).save(product);
    }
}