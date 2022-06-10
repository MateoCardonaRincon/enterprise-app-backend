package com.mateocr.enterpriseapp.dto;

import com.mateocr.enterpriseapp.collections.Product;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class BillDTO {

    private String id;
    private String clientName;
    private LocalDate dateOfSale;
    private String sellerName;
    private List<Product> products = new ArrayList<>();
    private Integer totalPaid;
}
