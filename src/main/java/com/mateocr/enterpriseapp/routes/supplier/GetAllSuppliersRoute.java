package com.mateocr.enterpriseapp.routes.supplier;

import com.mateocr.enterpriseapp.dto.SupplierDTO;
import com.mateocr.enterpriseapp.usecase.supplier.GetAllSuppliersUseCase;
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

import java.util.function.Supplier;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class GetAllSuppliersRoute {

    @Bean
    @RouterOperation(path = "/supplier/getall", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = GetAllSuppliersUseCase.class, method = RequestMethod.GET, beanMethod = "get",
            operation = @Operation(operationId = "getAllSuppliers", responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation",
                            content = @Content(schema = @Schema(implementation = Supplier.class)))}
            ))
    public RouterFunction<ServerResponse> getAllSuppliers(GetAllSuppliersUseCase getAllSuppliersUseCase) {
        return route(GET("/supplier/getall"),
                request -> ServerResponse.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllSuppliersUseCase.get(), SupplierDTO.class)));
    }
}
