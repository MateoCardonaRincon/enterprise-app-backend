package com.mateocr.enterpriseapp.collections;

import com.mateocr.enterpriseapp.dto.ProductDTO;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Document("bill")
public class Bill {

    @Id
    private String id;
    private LocalDate dateOfSale;
    private String customerName;
    private String sellerName;
    private List<ProductDTO> soldProducts = new ArrayList<>();
    private Double totalPaid;

    public Bill() {
    }

    public Bill(String id, LocalDate dateOfSale, String customerName, String sellerName, List<ProductDTO> soldProducts, Double totalPaid) {
        this.id = id;
        this.dateOfSale = dateOfSale;
        this.customerName = customerName;
        this.sellerName = sellerName;
        this.soldProducts = soldProducts;
        this.totalPaid = totalPaid;
    }

    public Bill(LocalDate dateOfSale, String customerName, String sellerName, List<ProductDTO> soldProducts, Double totalPaid) {
        this.dateOfSale = dateOfSale;
        this.customerName = customerName;
        this.sellerName = sellerName;
        this.soldProducts = soldProducts;
        this.totalPaid = totalPaid;
    }
}
