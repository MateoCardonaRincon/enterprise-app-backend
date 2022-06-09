package com.mateocr.enterpriseapp.routes;

import com.mateocr.enterpriseapp.dto.SupplierDTO;
import com.mateocr.enterpriseapp.usecase.SubscribeSupplierUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
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

}
