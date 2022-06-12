package com.mateocr.enterpriseapp.routes;

import com.mateocr.enterpriseapp.collections.Bill;
import com.mateocr.enterpriseapp.collections.Order;
import com.mateocr.enterpriseapp.collections.Product;
import com.mateocr.enterpriseapp.dto.BillDTO;
import com.mateocr.enterpriseapp.usecase.bill.CreateBillUseCase;
import com.mateocr.enterpriseapp.usecase.bill.GetAllBillsUseCase;
import com.mateocr.enterpriseapp.usecase.order.CreateOrderUseCase;
import com.mateocr.enterpriseapp.usecase.product.GetAllProductsUseCase;
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
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class BillRoute {

    @Bean
    @RouterOperation(path = "/bill/create", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.POST,
            beanClass = CreateBillUseCase.class, beanMethod = "apply",
            operation = @Operation(operationId = "createBill", responses = {
                    @ApiResponse(responseCode = "201",
                            description = "successful operation",
                            content = @Content(schema = @Schema(implementation = Bill.class))),
                    @ApiResponse(responseCode = "400", description = "bad request: bill not specified properly")},
                    requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Bill.class)))
            ))
    public RouterFunction<ServerResponse> createBill(CreateBillUseCase createBillUseCase) {
        Function<BillDTO, Mono<ServerResponse>> executor =
                billDTO -> createBillUseCase.apply(billDTO)
                        .flatMap(thisBill -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(thisBill));
        return route(
                POST("/bill/create").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(BillDTO.class).flatMap(executor)
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.BAD_REQUEST).build()));
    }

    @Bean
    @RouterOperation(path = "/bill/getall", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = GetAllBillsUseCase.class, method = RequestMethod.GET, beanMethod = "apply",
            operation = @Operation(operationId = "getBills", responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation",
                            content = @Content(schema = @Schema(implementation = Bill.class)))}
            ))
    public RouterFunction<ServerResponse> getAllBills(GetAllBillsUseCase getAllBillsUseCase) {
        return route(GET("/bill/getall"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllBillsUseCase.get(), BillDTO.class)));
    }
}
