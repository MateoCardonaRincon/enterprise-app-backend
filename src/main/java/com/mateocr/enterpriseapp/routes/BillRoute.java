package com.mateocr.enterpriseapp.routes;

import com.mateocr.enterpriseapp.dto.BillDTO;
import com.mateocr.enterpriseapp.usecase.bill.CreateBillUseCase;
import com.mateocr.enterpriseapp.usecase.bill.GetAllBillsUseCase;
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
public class BillRoute {

    @Bean
    public RouterFunction<ServerResponse> createBill(CreateBillUseCase createBillUseCase) {
        Function<BillDTO, Mono<ServerResponse>> executor =
                billDTO -> createBillUseCase.apply(billDTO)
                        .flatMap(thisBill -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(thisBill));
        return route(
                POST("/bill/create").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(BillDTO.class).flatMap(executor));
    }

    @Bean
    public RouterFunction<ServerResponse> getAllBills(GetAllBillsUseCase getAllBillsUseCase) {
        return route(GET("/bill/getall"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllBillsUseCase.get(), BillDTO.class)));
    }
}
