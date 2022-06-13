package com.mateocr.enterpriseapp.routes.supplier;

import com.mateocr.enterpriseapp.usecase.supplier.DeleteSupplierUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class DeleteSupplierRoute {

    @Bean
    @RouterOperation(path = "/supplier/delete/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.DELETE,
            beanClass = DeleteSupplierUseCase.class,
            beanMethod = "apply",
            operation = @Operation(operationId = "deleteSupplier", responses = {
                    @ApiResponse(responseCode = "202", description = "successful operation"),
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
