package com.mateocr.enterpriseapp.usecase.product;


import com.mateocr.enterpriseapp.collections.Supplier;
import com.mateocr.enterpriseapp.dto.ProductDTO;
import com.mateocr.enterpriseapp.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import com.mateocr.enterpriseapp.collections.Product;
import com.mateocr.enterpriseapp.mapper.ProductMapper;

@SpringBootTest
class GetAllProductsUseCaseTest {

    private GetAllProductsUseCase useCase;

    @Autowired
    private ProductMapper mapper;

    @Mock
    ProductRepository repository;

    @BeforeEach
    void setUp() {
        useCase = new GetAllProductsUseCase(repository, mapper);
    }

    @Test
    void getAllProducts() {


        Supplier supplier = new Supplier();
        supplier.setId("123");
        supplier.setName("homecenter");
        supplier.setNotes("screws, hammer");
        supplier.setPhoneNumber("123456");
        supplier.setPersonalId("321654");

        Product product = new Product("123", "screw", "steel screw", 50,20,100,150., supplier,0);

        ProductDTO productDTO = mapper.convertCollectionToProductDTO().apply(product);


        Mockito.when(repository.findAll()).thenReturn(Flux.just(product));
        Flux<ProductDTO> productDTOFlux = useCase.get();

        StepVerifier.create(productDTOFlux)
                .expectNext(productDTO)
                .verifyComplete();

        Mockito.verify(repository).findAll();
    }

}