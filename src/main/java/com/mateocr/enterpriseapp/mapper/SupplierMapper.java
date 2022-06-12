package com.mateocr.enterpriseapp.mapper;

import com.mateocr.enterpriseapp.collections.Supplier;
import com.mateocr.enterpriseapp.dto.SupplierDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.config.EnableWebFlux;

import java.util.function.Function;

@EnableWebFlux
@Component
@RequiredArgsConstructor
public class SupplierMapper {

    private final ModelMapper modelMapper;

    public Function<SupplierDTO, Supplier> convertSupplierDTOToCollection() {
        return supplierDTO -> modelMapper.map(supplierDTO, Supplier.class);
    }

    public Function<Supplier, SupplierDTO> convertCollectionToSupplierDTO() {
        return supplier -> modelMapper.map(supplier, SupplierDTO.class);
    }
}
