package com.mateocr.enterpriseapp.routes;

import com.mateocr.enterpriseapp.collections.Bill;
import com.mateocr.enterpriseapp.dto.SupplierDTO;
import com.mateocr.enterpriseapp.usecase.bill.CreateBillUseCase;
import com.mateocr.enterpriseapp.usecase.bill.GetAllBillsUseCase;
import com.mateocr.enterpriseapp.usecase.product.DeleteProductUseCase;
import com.mateocr.enterpriseapp.usecase.supplier.DeleteSupplierUseCase;
import com.mateocr.enterpriseapp.usecase.supplier.GetAllSuppliersUseCase;
import com.mateocr.enterpriseapp.usecase.supplier.SubscribeSupplierUseCase;
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
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.function.Supplier;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class SupplierRoute {

    @Bean
    @RouterOperation(path = "/supplier/subscribe", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.POST,
            beanClass = SubscribeSupplierUseCase.class, beanMethod = "apply",
            operation = @Operation(operationId = "createSupplier", responses = {
                    @ApiResponse(responseCode = "201",
                            description = "successful operation",
                            content = @Content(schema = @Schema(implementation = Supplier.class))),
                    @ApiResponse(responseCode = "400", description = "bad request: supplier not specified properly")},
                    requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Supplier.class)))
            ))
    public RouterFunction<ServerResponse> subscribeSupplier(SubscribeSupplierUseCase subscribeSupplierUseCase) {
        Function<SupplierDTO, Mono<ServerResponse>> executor =
                supplierDTO -> subscribeSupplierUseCase.apply(supplierDTO)
                        .flatMap(thisSupplier -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(thisSupplier));
        return route(
                POST("/supplier/subscribe").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(SupplierDTO.class).flatMap(executor)
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.BAD_REQUEST).build()));
    }

    @Bean
    @RouterOperation(path = "/supplier/getall", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = GetAllSuppliersUseCase.class, method = RequestMethod.GET, beanMethod = "apply",
            operation = @Operation(operationId = "getSuppliers", responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation",
                            content = @Content(schema = @Schema(implementation = Supplier.class)))}
            ))
    public RouterFunction<ServerResponse> getAllSuppliers(GetAllSuppliersUseCase getAllSuppliersUseCase) {
        return route(GET("/supplier/getall"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllSuppliersUseCase.get(), SupplierDTO.class)));
    }

    @Bean
    @RouterOperation(path = "/supplier/delete/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.DELETE,
            beanClass = DeleteSupplierUseCase.class,
            beanMethod = "apply",
            operation = @Operation(operationId = "deleteSupplier", responses = {
                    @ApiResponse(responseCode = "202",description = "successful operation"),
                    @ApiResponse(responseCode = "404", description = "Supplier not found")}, parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "id")}
            ))
    public RouterFunction<ServerResponse> deleteSupplier(DeleteSupplierUseCase deleteSupplierUseCase) {
        return route(DELETE("/supplier/delete/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> deleteSupplierUseCase.apply(request.pathVariable("id"))
                        .flatMap(unused -> ServerResponse.status(HttpStatus.ACCEPTED).build())
                        .onErrorResume(e -> ServerResponse.status(HttpStatus.NOT_FOUND).build())
        );
    }

}
