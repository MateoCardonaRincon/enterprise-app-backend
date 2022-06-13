package com.mateocr.enterpriseapp.routes.order;

import com.mateocr.enterpriseapp.collections.Order;
import com.mateocr.enterpriseapp.dto.OrderDTO;
import com.mateocr.enterpriseapp.usecase.order.CreateOrderUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class CreateOrderRoute {

    @Bean
    @RouterOperation(path = "/order/create", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.POST,
            beanClass = CreateOrderUseCase.class, beanMethod = "apply",
            operation = @Operation(operationId = "createOrder", responses = {
                    @ApiResponse(responseCode = "201",
                            description = "successful operation",
                            content = @Content(schema = @Schema(implementation = OrderDTO.class))),
                    @ApiResponse(responseCode = "400", description = "bad request: order not specified properly")},
                    requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = OrderDTO.class)))
            ))
    public RouterFunction<ServerResponse> createOrder(CreateOrderUseCase createOrderUseCase) {
        Function<OrderDTO, Mono<ServerResponse>> executor =
                orderDTO -> createOrderUseCase.apply(orderDTO)
                        .flatMap(thisOrder -> ServerResponse.status(HttpStatus.CREATED)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(thisOrder));
        return route(
                POST("/order/create").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(OrderDTO.class).flatMap(executor)
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.BAD_REQUEST).build()));
    }
}
