package com.mateocr.enterpriseapp.usecase.order;

import com.mateocr.enterpriseapp.dto.OrderDTO;
import com.mateocr.enterpriseapp.mapper.OrderMapper;
import com.mateocr.enterpriseapp.repository.OrderRepository;
import com.mateocr.enterpriseapp.usecase.order.interfaces.CreateOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
@RequiredArgsConstructor
public class CreateOrderUseCase implements CreateOrder {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public Mono<OrderDTO> apply(OrderDTO orderDTO) {
        return orderRepository.save(orderMapper.convertOrderDTOToCollection().apply(orderDTO))
                .map(order -> orderMapper.convertCollectionToOrderDTO().apply(order));
    }
}
