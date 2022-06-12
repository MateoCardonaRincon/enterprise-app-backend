package com.mateocr.enterpriseapp.usecase.bill;

import com.mateocr.enterpriseapp.collections.Bill;
import com.mateocr.enterpriseapp.collections.Supplier;
import com.mateocr.enterpriseapp.dto.BillDTO;
import com.mateocr.enterpriseapp.dto.ProductDTO;
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

        ProductDTO product1 = new ProductDTO();
        product1.setId("132");
        product1.setName("screw");
        product1.setDescription("steel 1-in");
        product1.setStock(100);
        product1.setMinimumAmount(100);
        product1.setMaximumAmount(200);
        product1.setPrice(100.);
        product1.setSoldUnits(30);
        product1.setSupplier(supplier);

        Bill bill = new Bill("123",
                LocalDate.now(),
                "Mateo",
                "Don Raul",
                List.of(product1),
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