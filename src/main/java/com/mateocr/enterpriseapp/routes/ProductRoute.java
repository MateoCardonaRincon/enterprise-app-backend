package com.mateocr.enterpriseapp.routes;

import com.mateocr.enterpriseapp.collections.Product;
import com.mateocr.enterpriseapp.dto.BillDTO;
import com.mateocr.enterpriseapp.dto.ProductDTO;
import com.mateocr.enterpriseapp.usecase.product.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ProductRoute {

    @Bean
    @RouterOperation(path = "/product/create", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.POST,
            beanClass = CreateProductUseCase.class, beanMethod = "apply",
            operation = @Operation(operationId = "createProduct", responses = {
                    @ApiResponse(responseCode = "201",
                            description = "successful operation",
                            content = @Content(schema = @Schema(implementation = Product.class))),
                    @ApiResponse(responseCode = "400", description = "bad request: product not specified properly")},
                    requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Product.class)))
            ))
    public RouterFunction<ServerResponse> createProduct(CreateProductUseCase createProductUseCase) {
        Function<ProductDTO, Mono<ServerResponse>> executor =
                productDTO -> createProductUseCase.apply(productDTO)
                        .flatMap(thisProduct -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(thisProduct));
        return route(
                POST("/product/create").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(ProductDTO.class).flatMap(executor)
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.BAD_REQUEST).build()));
    }

    @Bean
    @RouterOperation(path = "/product/getall", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = GetAllProductsUseCase.class, method = RequestMethod.GET, beanMethod = "apply",
            operation = @Operation(operationId = "getProducts", responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation",
                            content = @Content(schema = @Schema(implementation = Product.class)))}
            ))
    public RouterFunction<ServerResponse> getAllProducts(GetAllProductsUseCase getAllProductsUseCase) {
        return route(GET("/product/getall"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllProductsUseCase.get(), ProductDTO.class)));
    }

    @Bean
    @RouterOperation(path = "/product/restock/{units}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.PUT,
            beanClass = RestockProductUseCase.class,
            beanMethod = "apply",
            operation = @Operation(operationId = "updateProduct", responses = {
                    @ApiResponse(responseCode = "202",
                            description = "successful operation",
                            content = @Content(schema = @Schema(implementation = Product.class))),
                    @ApiResponse(responseCode = "400",
                            description = "bad request: product not specified properly")},
                    requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Product.class)))
            ))
    RouterFunction<ServerResponse> restockProduct(RestockProductUseCase restockProductUseCase) {

        return route(PUT("/product/restock/{units}").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(ProductDTO.class)
                        .flatMap(productDTO -> restockProductUseCase.apply(productDTO, Integer.parseInt(request.pathVariable("units"))))
                        .flatMap(result -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(result))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.BAD_REQUEST).build()));
    }
}
