package com.mateocr.enterpriseapp.routes;

import com.mateocr.enterpriseapp.dto.SupplierDTO;
import com.mateocr.enterpriseapp.usecase.supplier.DeleteSupplierUseCase;
import com.mateocr.enterpriseapp.usecase.supplier.GetAllSuppliersUseCase;
import com.mateocr.enterpriseapp.usecase.supplier.SubscribeSupplierUseCase;
import com.mateocr.enterpriseapp.usecase.supplier.interfaces.DeleteSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class SupplierRoute {

    @Bean
    public RouterFunction<ServerResponse> subscribeSupplier(SubscribeSupplierUseCase subscribeSupplierUseCase) {
        Function<SupplierDTO, Mono<ServerResponse>> executor =
                supplierDTO -> subscribeSupplierUseCase.apply(supplierDTO)
                        .flatMap(thisSupplier -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(thisSupplier));
        return route(
                POST("/supplier/subscribe").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(SupplierDTO.class).flatMap(executor));
    }

    @Bean
    public RouterFunction<ServerResponse> getAllSuppliers(GetAllSuppliersUseCase getAllSuppliersUseCase) {
        return route(GET("supplier/getall"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllSuppliersUseCase.get(), SupplierDTO.class)));
    }

    @Bean
    public RouterFunction<ServerResponse> deleteSupplier(DeleteSupplierUseCase deleteSupplierUseCase) {
        return route(DELETE("supplier/delete/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.noContent()
                        .build(deleteSupplierUseCase.apply(request.pathVariable("id"))));
    }

}
