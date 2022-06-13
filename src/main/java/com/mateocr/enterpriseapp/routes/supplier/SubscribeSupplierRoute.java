package com.mateocr.enterpriseapp.routes.supplier;

import com.mateocr.enterpriseapp.dto.SupplierDTO;
import com.mateocr.enterpriseapp.usecase.supplier.SubscribeSupplierUseCase;
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
import java.util.function.Supplier;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class SubscribeSupplierRoute {

    @Bean
    @RouterOperation(path = "/supplier/subscribe", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.POST,
            beanClass = SubscribeSupplierUseCase.class, beanMethod = "apply",
            operation = @Operation(operationId = "subscribeSupplier", responses = {
                    @ApiResponse(responseCode = "201",
                            description = "successful operation",
                            content = @Content(schema = @Schema(implementation = Supplier.class))),
                    @ApiResponse(responseCode = "400", description = "bad request: supplier not specified properly")},
                    requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Supplier.class)))
            ))
    public RouterFunction<ServerResponse> subscribeSupplier(SubscribeSupplierUseCase subscribeSupplierUseCase) {
        Function<SupplierDTO, Mono<ServerResponse>> executor =
                supplierDTO -> subscribeSupplierUseCase.apply(supplierDTO)
                        .flatMap(thisSupplier -> ServerResponse.status(HttpStatus.CREATED)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(thisSupplier));
        return route(
                POST("/supplier/subscribe").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(SupplierDTO.class).flatMap(executor)
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.BAD_REQUEST).build()));
    }
}
