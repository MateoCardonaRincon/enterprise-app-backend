package com.mateocr.enterpriseapp.mapper;

import com.mateocr.enterpriseapp.collections.Order;
import com.mateocr.enterpriseapp.dto.OrderDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final ModelMapper modelMapper;

    public Function<OrderDTO, Order> convertOrderDTOToCollection() {
        return orderDTO -> modelMapper.map(orderDTO, Order.class);
    }

    public Function<Order, OrderDTO> convertCollectionToOrderDTO() {
        return order -> modelMapper.map(order, OrderDTO.class);
    }
}
