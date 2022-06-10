package com.mateocr.enterpriseapp.routes;

import com.mateocr.enterpriseapp.dto.OrderDTO;
import com.mateocr.enterpriseapp.usecase.order.CreateOrderUseCase;
import com.mateocr.enterpriseapp.usecase.order.GetAllOrdersUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class OrderRoute {

    @Bean
    public RouterFunction<ServerResponse> createOrder(CreateOrderUseCase createOrderUseCase) {
        Function<OrderDTO, Mono<ServerResponse>> executor =
                orderDTO -> createOrderUseCase.apply(orderDTO)
                        .flatMap(thisOrder -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(thisOrder));
        return route(
                POST("/order/create").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(OrderDTO.class).flatMap(executor));
    }

    @Bean
    public RouterFunction<ServerResponse> getAllOrders(GetAllOrdersUseCase getAllOrdersUseCase) {
        return route(GET("/order/getall"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllOrdersUseCase.get(), OrderDTO.class)));
    }
}
