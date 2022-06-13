package com.mateocr.enterpriseapp.routes.order;

import com.mateocr.enterpriseapp.dto.OrderDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class GetAllOrdersRoute {

    @Bean
    @RouterOperation(path = "/order/getall", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = com.mateocr.enterpriseapp.usecase.order.GetAllOrdersUseCase.class, method = RequestMethod.GET, beanMethod = "get",
            operation = @Operation(operationId = "getAllOrders", responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation",
                            content = @Content(schema = @Schema(implementation = OrderDTO.class)))}
            ))
    public RouterFunction<ServerResponse> getAllOrders(com.mateocr.enterpriseapp.usecase.order.GetAllOrdersUseCase getAllOrdersUseCase) {
        return route(GET("/order/getall"),
                request -> ServerResponse.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllOrdersUseCase.get(), OrderDTO.class)));
    }
}
