package com.mateocr.enterpriseapp.usecase.supplier;

import com.mateocr.enterpriseapp.collections.Supplier;
import com.mateocr.enterpriseapp.mapper.SupplierMapper;
import com.mateocr.enterpriseapp.repository.SupplierRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class DeleteSupplierUseCaseTest {

    @SpyBean
    private DeleteSupplierUseCase useCase;

    @Autowired
    private SupplierMapper mapper;

    @MockBean
    SupplierRepository repository;

    @Test
    void deleteSupplier() {


        Supplier supplier = new Supplier();
        supplier.setId("123");
        supplier.setName("homecenter");
        supplier.setNotes("screws, hammer");
        supplier.setPhoneNumber("123456");
        supplier.setPersonalId("321654");

        Mockito.when(repository.findById(supplier.getId())).thenReturn(Mono.just(supplier));

        Mockito.when(repository.deleteById(supplier.getId())).thenReturn(Mono.empty());

        Mono<Void> emptyMono = useCase.apply(supplier.getId());

        StepVerifier.create(emptyMono)
                .expectNextCount(0)
                .verifyComplete();

        Mockito.verify(repository).findById(supplier.getId());
        Mockito.verify(repository).deleteById(supplier.getId());
    }

}