package com.mateocr.enterpriseapp.usecase.supplier;

import com.mateocr.enterpriseapp.collections.Product;
import com.mateocr.enterpriseapp.collections.Supplier;
import com.mateocr.enterpriseapp.dto.ProductDTO;
import com.mateocr.enterpriseapp.dto.SupplierDTO;
import com.mateocr.enterpriseapp.mapper.ProductMapper;
import com.mateocr.enterpriseapp.mapper.SupplierMapper;
import com.mateocr.enterpriseapp.repository.ProductRepository;
import com.mateocr.enterpriseapp.repository.SupplierRepository;
import com.mateocr.enterpriseapp.usecase.product.GetAllProductsUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
class GetAllSuppliersUseCaseTest {

    private GetAllSuppliersUseCase useCase;

    @Autowired
    private SupplierMapper mapper;

    @Mock
    SupplierRepository repository;

    @BeforeEach
    void setUp() {
        useCase = new GetAllSuppliersUseCase(repository, mapper);
    }

    @Test
    void getAllOrders() {


        Supplier supplier = new Supplier();
        supplier.setId("123");
        supplier.setName("homecenter");
        supplier.setNotes("screws, hammer");
        supplier.setPhoneNumber("123456");
        supplier.setPersonalId("321654");

        SupplierDTO supplierDTO = mapper.convertCollectionToSupplierDTO().apply(supplier);

        Mockito.when(repository.findAll()).thenReturn(Flux.just(supplier));

        Flux<SupplierDTO> supplierDTOFlux = useCase.get();

        StepVerifier.create(supplierDTOFlux)
                .expectNext(supplierDTO)
                .verifyComplete();

        Mockito.verify(repository).findAll();
    }

}