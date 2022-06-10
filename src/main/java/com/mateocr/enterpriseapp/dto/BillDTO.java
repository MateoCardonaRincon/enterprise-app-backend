package com.mateocr.enterpriseapp.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class BillDTO {

    private String id;
    private LocalDate dateOfSale;
    private String customerName;
    private String sellerName;
    private List<ProductDTO> soldProducts = new ArrayList<>();
    private Double totalPaid;
}
