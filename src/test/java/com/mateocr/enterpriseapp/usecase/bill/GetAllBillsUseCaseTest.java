package com.mateocr.enterpriseapp.usecase.bill;

import com.mateocr.enterpriseapp.collections.Bill;
import com.mateocr.enterpriseapp.collections.Product;
import com.mateocr.enterpriseapp.collections.Supplier;
import com.mateocr.enterpriseapp.dto.BillDTO;
import com.mateocr.enterpriseapp.mapper.BillMapper;
import com.mateocr.enterpriseapp.repository.BillRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class GetAllBillsUseCaseTest {

    private GetAllBillsUseCase useCase;

    @Autowired
    private BillMapper mapper;

    @Mock
    BillRepository repository;

    @BeforeEach
    void setUp() {
        useCase = new GetAllBillsUseCase(repository, mapper);
    }

    @Test
    void getAllBills() {

        Supplier supplier = new Supplier();
        supplier.setId("123");
        supplier.setName("homecenter");
        supplier.setNotes("screws, hammer");
        supplier.setPhoneNumber("123456");
        supplier.setPersonalId("321654");

        Product product = new Product();
        product.setId("132");
        product.setName("screw");
        product.setDescription("steel 1-in");
        product.setStock(100);
        product.setMinimumAmount(100);
        product.setMaximumAmount(200);
        product.setPrice(100.);
        product.setSoldUnits(30);
        product.setSupplier(supplier);

        Bill bill = new Bill("123",
                LocalDate.now(),
                "Mateo",
                "Don Raul",
                List.of(product),
                10000.);

        BillDTO billDTO = mapper.convertCollectionToBillDTO().apply(bill);

        Mockito.when(repository.findAll()).thenReturn(Flux.just(bill));
        Flux<BillDTO> billDTOFlux = useCase.get();

        StepVerifier.create(billDTOFlux)
                .expectNext(billDTO)
                .verifyComplete();

        Mockito.verify(repository).findAll();
    }
}