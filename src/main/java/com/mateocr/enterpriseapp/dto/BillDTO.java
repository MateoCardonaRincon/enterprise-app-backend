package com.mateocr.enterpriseapp.dto;

import com.mateocr.enterpriseapp.collections.Product;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class BillDTO {

    private String id;
    @NotBlank
    private LocalDate dateOfSale;
    @NotBlank
    private String customerName;
    @NotBlank
    private String sellerName;
    @NotNull
    private List<Product> soldProducts = new ArrayList<>();
    @NotNull
    private Double totalPaid;
}
