package com.mateocr.enterpriseapp.routes;

import com.mateocr.enterpriseapp.dto.ProductDTO;
import com.mateocr.enterpriseapp.usecase.product.CreateProductUseCase;
import com.mateocr.enterpriseapp.usecase.product.DeleteProductUseCase;
import com.mateocr.enterpriseapp.usecase.product.GetAllProductsUseCase;
import com.mateocr.enterpriseapp.usecase.product.RestockProductUseCase;
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
public class ProductRoute {

    @Bean
    public RouterFunction<ServerResponse> createProduct(CreateProductUseCase createProductUseCase) {
        Function<ProductDTO, Mono<ServerResponse>> executor =
                productDTO -> createProductUseCase.apply(productDTO)
                        .flatMap(thisProduct -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(thisProduct));
        return route(
                POST("/product/create").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(ProductDTO.class).flatMap(executor));
    }

    @Bean
    public RouterFunction<ServerResponse> getAllProducts(GetAllProductsUseCase getAllProductsUseCase) {
        return route(GET("/product/getall"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllProductsUseCase.get(), ProductDTO.class)));
    }

    @Bean
    RouterFunction<ServerResponse> restockProduct(RestockProductUseCase restockProductUseCase){

        return route(PUT("/product/restock/{units}").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(ProductDTO.class)
                        .flatMap(productDTO -> restockProductUseCase.apply(productDTO, Integer.parseInt(request.pathVariable("units"))))
                        .flatMap(result -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(result)));
    }

    @Bean
    public RouterFunction<ServerResponse> deleteProduct(DeleteProductUseCase deleteProductUseCase) {
        return route(DELETE("/product/delete/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.noContent()
                        .build(deleteProductUseCase.apply(request.pathVariable("id"))));
    }
}
