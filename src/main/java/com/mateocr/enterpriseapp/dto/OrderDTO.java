package com.mateocr.enterpriseapp.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class OrderDTO {

    private String id;
    private LocalDate dateOfOrder;
    private ProductDTO product;
    private Integer units;
}
