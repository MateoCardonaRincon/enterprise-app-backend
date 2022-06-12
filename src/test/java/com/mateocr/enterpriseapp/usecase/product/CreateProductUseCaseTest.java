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

@SpringBootTest
class CreateProductUseCaseTest {

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper mapper;

    @SpyBean
    private CreateProductUseCase useCase;

    @Test
    @DisplayName("Create product")
    void createProduct() {

        Supplier supplier = new Supplier();
        supplier.setId("123");
        supplier.setName("homecenter");
        supplier.setNotes("screws, hammer");
        supplier.setPhoneNumber("123456");
        supplier.setPersonalId("321654");

        Product product = new Product("123", "screw", "steel screw", 50, 20, 100, 150., supplier, 0);

        ProductDTO productDTO = mapper.convertCollectionToProductDTO().apply(product);

        Mockito.when(productRepository.save(product)).thenReturn(Mono.just(product));

        var result = useCase.apply(productDTO);


        Assertions.assertEquals(product.getId(), result.block().getId());
        Assertions.assertEquals(product.getName(), result.block().getName());
        Assertions.assertEquals(product.getDescription(), result.block().getDescription());
        Assertions.assertEquals(product.getStock(), result.block().getStock());
        Assertions.assertEquals(product.getPrice(), result.block().getPrice());
        Assertions.assertEquals(product.getMaximumAmount(), result.block().getMaximumAmount());
        Assertions.assertEquals(product.getMinimumAmount(), result.block().getMinimumAmount());
        Assertions.assertEquals(product.getSoldUnits(), result.block().getSoldUnits());
        Assertions.assertEquals(product.getSupplier(), result.block().getSupplier());

        StepVerifier
                .create(Mono.just(Mockito.when(useCase.apply(productDTO))
                        .thenReturn(Mono.just(productDTO)))).expectNextCount(1).expectComplete().verify();

        Mockito.verify(productRepository).save(product);
    }

}