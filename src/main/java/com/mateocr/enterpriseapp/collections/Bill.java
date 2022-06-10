package com.mateocr.enterpriseapp.collections;

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
    private String clientName;
    private LocalDate dateOfSale;
    private String sellerName;
    private List<Product> products = new ArrayList<>();
    private Integer totalPaid;

    public Bill() {
    }

    public Bill(String id,
                String clientName,
                LocalDate dateOfSale,
                String sellerName,
                List<Product> products,
                Integer totalPaid) {
        this.id = id;
        this.clientName = clientName;
        this.dateOfSale = dateOfSale;
        this.sellerName = sellerName;
        this.products = products;
        this.totalPaid = totalPaid;
    }

    public Bill(String clientName,
                LocalDate dateOfSale,
                String sellerName,
                List<Product> products,
                Integer totalPaid) {
        this.clientName = clientName;
        this.dateOfSale = dateOfSale;
        this.sellerName = sellerName;
        this.products = products;
        this.totalPaid = totalPaid;
    }
}
