package com.mateocr.enterpriseapp.dto;

import com.mateocr.enterpriseapp.collections.Product;
import com.mateocr.enterpriseapp.collections.Supplier;
import lombok.Data;

import java.time.LocalDate;

@Data
public class OrderDTO {

    private String id;
    private LocalDate dateOfOrder;
    private Product product;
    private Integer units;
    private Supplier supplier;
}
