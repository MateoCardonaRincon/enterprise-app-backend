package com.mateocr.enterpriseapp.mapper;

import com.mateocr.enterpriseapp.collections.Product;
import com.mateocr.enterpriseapp.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final ModelMapper modelMapper;

    public Function<ProductDTO, Product> convertProductDTOToCollection() {
        return supplierDTO -> modelMapper.map(supplierDTO, Product.class);
    }

    public Function<Product, ProductDTO> convertCollectionToProductDTO() {
        return supplier -> modelMapper.map(supplier, ProductDTO.class);
    }
}
