package com.mateocr.enterpriseapp.dto;

import com.mateocr.enterpriseapp.collections.Product;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class OrderDTO {

    private String id;
    @NotBlank
    private LocalDate dateOfOrder;
    @NotNull
    private Product product;
    @NotNull
    private Integer units;
}
