package com.mateocr.enterpriseapp.usecase.order;

import com.mateocr.enterpriseapp.collections.Order;
import com.mateocr.enterpriseapp.collections.Supplier;
import com.mateocr.enterpriseapp.dto.OrderDTO;
import com.mateocr.enterpriseapp.dto.ProductDTO;
import com.mateocr.enterpriseapp.mapper.OrderMapper;
import com.mateocr.enterpriseapp.repository.OrderRepository;
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
@SpringBootTest
class CreateOrderUseCaseTest {

    @MockBean
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper mapper;

    @SpyBean
    private CreateOrderUseCase useCase;

    @Test
    @DisplayName("Create order")
    void createOrder() {

        Supplier supplier = new Supplier();
        supplier.setId("123");
        supplier.setName("homecenter");
        supplier.setNotes("screws, hammer");
        supplier.setPhoneNumber("123456");
        supplier.setPersonalId("321654");

        ProductDTO product = new ProductDTO();
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

        Mockito.when(orderRepository.save(order)).thenReturn(Mono.just(order));

        var result = useCase.apply(orderDTO);


        Assertions.assertEquals(order.getId(), result.block().getId());
        Assertions.assertEquals(order.getDateOfOrder(), result.block().getDateOfOrder());
        Assertions.assertEquals(order.getUnits(), result.block().getUnits());
        Assertions.assertEquals(order.getProduct(), result.block().getProduct());

        StepVerifier
                .create(Mono.just(Mockito.when(useCase.apply(orderDTO))
                        .thenReturn(Mono.just(orderDTO)))).expectNextCount(1).expectComplete().verify();

        Mockito.verify(orderRepository).save(order);
    }

}