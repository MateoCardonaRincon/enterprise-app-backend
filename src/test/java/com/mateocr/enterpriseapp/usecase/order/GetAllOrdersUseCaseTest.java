package com.mateocr.enterpriseapp.usecase.order;

import com.mateocr.enterpriseapp.collections.Order;
import com.mateocr.enterpriseapp.collections.Product;
import com.mateocr.enterpriseapp.collections.Supplier;
import com.mateocr.enterpriseapp.dto.OrderDTO;
import com.mateocr.enterpriseapp.mapper.OrderMapper;
import com.mateocr.enterpriseapp.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.LocalDate;

@SpringBootTest
class GetAllOrdersUseCaseTest {

    private GetAllOrdersUseCase useCase;

    @Autowired
    private OrderMapper mapper;

    @Mock
    OrderRepository repository;

    @BeforeEach
    void setUp() {
        useCase = new GetAllOrdersUseCase(repository, mapper);
    }

    @Test
    void getAllOrders() {


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

        Order order = new Order("xxxxx", LocalDate.now(), product, 20);

        OrderDTO orderDTO = mapper.convertCollectionToOrderDTO().apply(order);


        Mockito.when(repository.findAll()).thenReturn(Flux.just(order));
        Flux<OrderDTO> orderDTOFlux = useCase.get();

        StepVerifier.create(orderDTOFlux)
                .expectNext(orderDTO)
                .verifyComplete();

        Mockito.verify(repository).findAll();
    }

}