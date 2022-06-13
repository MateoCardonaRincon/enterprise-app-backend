package com.mateocr.enterpriseapp.dto;

import com.mateocr.enterpriseapp.collections.Supplier;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ProductDTO {

    private String id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private Integer stock;
    @NotNull
    private Integer minimumAmount;
    @NotNull
    private Integer maximumAmount;
    @NotNull
    private Double price;
    @NotNull
    private Supplier supplier;
    @NotNull
    private Integer soldUnits;
}
