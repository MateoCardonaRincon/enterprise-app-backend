package com.mateocr.enterpriseapp.routes.product;

import com.mateocr.enterpriseapp.collections.Product;
import com.mateocr.enterpriseapp.dto.ProductDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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

import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RestockProductRoute {

    @Bean
    @RouterOperation(path = "/product/restock/{units}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.PUT,
            beanClass = com.mateocr.enterpriseapp.usecase.product.RestockProductUseCase.class,
            beanMethod = "apply",
            operation = @Operation(operationId = "restockProduct", responses = {
                    @ApiResponse(responseCode = "202",
                            description = "successful operation",
                            content = @Content(schema = @Schema(implementation = Product.class))),
                    @ApiResponse(responseCode = "400",
                            description = "bad request: product not specified properly")},
                    parameters = {@Parameter(in = ParameterIn.PATH, name = "units")},
                    requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Product.class)))
            ))
    RouterFunction<ServerResponse> restockProduct(com.mateocr.enterpriseapp.usecase.product.RestockProductUseCase restockProductUseCase) {

        return route(PUT("/product/restock/{units}").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(ProductDTO.class)
                        .flatMap(productDTO -> restockProductUseCase.apply(productDTO, Integer.parseInt(request.pathVariable("units"))))
                        .flatMap(result -> ServerResponse.status(HttpStatus.ACCEPTED)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(result))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.BAD_REQUEST).build()));
    }
}
