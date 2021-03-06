package com.mateocr.enterpriseapp.usecase.bill;

import com.mateocr.enterpriseapp.collections.Bill;
import com.mateocr.enterpriseapp.collections.Product;
import com.mateocr.enterpriseapp.collections.Supplier;
import com.mateocr.enterpriseapp.dto.BillDTO;
import com.mateocr.enterpriseapp.mapper.BillMapper;
import com.mateocr.enterpriseapp.repository.BillRepository;
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

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class CreateBillUseCaseTest {

    @MockBean
    private BillRepository billRepository;

    @Autowired
    private BillMapper mapper;

    @SpyBean
    private CreateBillUseCase useCase;

    @Test
    @DisplayName("Create bill")
    void createBill() {

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

        Mockito.when(billRepository.save(bill)).thenReturn(Mono.just(bill));

        var result = useCase.apply(billDTO);


        Assertions.assertEquals(billDTO.getId(), result.block().getId());
        Assertions.assertEquals(billDTO.getCustomerName(), result.block().getCustomerName());
        Assertions.assertEquals(billDTO.getSellerName(), result.block().getSellerName());
        Assertions.assertEquals(billDTO.getDateOfSale(), result.block().getDateOfSale());
        Assertions.assertEquals(billDTO.getTotalPaid(), result.block().getTotalPaid());
        Assertions.assertEquals(billDTO.getSoldProducts().size(), result.block().getSoldProducts().size());

        StepVerifier
                .create(Mono.just(Mockito.when(useCase.apply(billDTO))
                        .thenReturn(Mono.just(billDTO)))).expectNextCount(1).expectComplete().verify();

        Mockito.verify(billRepository).save(bill);
    }

}
