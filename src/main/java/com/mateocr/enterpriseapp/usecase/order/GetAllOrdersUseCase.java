package com.mateocr.enterpriseapp.usecase.order;

import com.mateocr.enterpriseapp.dto.OrderDTO;
import com.mateocr.enterpriseapp.mapper.OrderMapper;
import com.mateocr.enterpriseapp.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class GetAllOrdersUseCase implements Supplier<Flux<OrderDTO>> {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public Flux<OrderDTO> get() {
        return orderRepository.findAll()
                .map(order -> orderMapper.convertCollectionToOrderDTO().apply(order));
    }
}
