package com.mateocr.enterpriseapp.routes;

import com.mateocr.enterpriseapp.dto.ProductDTO;
import com.mateocr.enterpriseapp.dto.SupplierDTO;
import com.mateocr.enterpriseapp.usecase.product.CreateProductUseCase;
import com.mateocr.enterpriseapp.usecase.supplier.SubscribeSupplierUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
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
}
