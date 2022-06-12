package com.mateocr.enterpriseapp.usecase.supplier;

import com.mateocr.enterpriseapp.collections.Product;
import com.mateocr.enterpriseapp.collections.Supplier;
import com.mateocr.enterpriseapp.dto.ProductDTO;
import com.mateocr.enterpriseapp.dto.SupplierDTO;
import com.mateocr.enterpriseapp.mapper.ProductMapper;
import com.mateocr.enterpriseapp.mapper.SupplierMapper;
import com.mateocr.enterpriseapp.repository.ProductRepository;
import com.mateocr.enterpriseapp.repository.SupplierRepository;
import com.mateocr.enterpriseapp.usecase.product.CreateProductUseCase;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SubscribeSupplierUseCaseTest {

    @MockBean
    private SupplierRepository supplierRepository;

    @Autowired
    private SupplierMapper mapper;

    @SpyBean
    private SubscribeSupplierUseCase useCase;

    @Test
    @DisplayName("Subscribe supplier")
    void subscribeSupplier() {

        Supplier supplier = new Supplier();
        supplier.setId("123");
        supplier.setName("homecenter");
        supplier.setNotes("screws, hammer");
        supplier.setPhoneNumber("123456");
        supplier.setPersonalId("321654");

        SupplierDTO supplierDTO = mapper.convertCollectionToSupplierDTO().apply(supplier);

        Mockito.when(supplierRepository.save(supplier)).thenReturn(Mono.just(supplier));

        var result = useCase.apply(supplierDTO);


        Assertions.assertEquals(supplier.getId(), result.block().getId());
        Assertions.assertEquals(supplier.getName(), result.block().getName());
        Assertions.assertEquals(supplier.getNotes(), result.block().getNotes());
        Assertions.assertEquals(supplier.getPersonalId(), result.block().getPersonalId());
        Assertions.assertEquals(supplier.getPhoneNumber(), result.block().getPhoneNumber());

        StepVerifier
                .create(Mono.just(Mockito.when(useCase.apply(supplierDTO))
                        .thenReturn(Mono.just(supplierDTO)))).expectNextCount(1).expectComplete().verify();

        Mockito.verify(supplierRepository).save(supplier);
    }


}