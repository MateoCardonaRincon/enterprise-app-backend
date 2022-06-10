package com.mateocr.enterpriseapp.dto;

import com.mateocr.enterpriseapp.collections.Supplier;
import lombok.Data;

@Data
public class ProductDTO {

    private String id;
    private String name;
    private String description;
    private Integer stock;
    private Integer minimumAmount;
    private Integer maximumAmount;
    private Double price;
    private Supplier supplier;
    private Integer soldUnits;
}
