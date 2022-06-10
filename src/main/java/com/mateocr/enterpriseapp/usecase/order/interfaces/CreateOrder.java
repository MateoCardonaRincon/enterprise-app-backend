package com.mateocr.enterpriseapp.usecase.order.interfaces;

import com.mateocr.enterpriseapp.dto.OrderDTO;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface CreateOrder {
    Mono<OrderDTO> apply(OrderDTO orderDTO);
}
