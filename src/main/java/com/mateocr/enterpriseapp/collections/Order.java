package com.mateocr.enterpriseapp.collections;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document("order")
public class Order {

    @Id
    private String id;
    private LocalDate dateOfOrder;
    private Product product;
    private Integer units;
    private Supplier supplier;

    public Order() {
    }

    public Order(String id,
                 LocalDate dateOfOrder,
                 Product product,
                 Integer units,
                 Supplier supplier) {
        this.id = id;
        this.dateOfOrder = dateOfOrder;
        this.product = product;
        this.units = units;
        this.supplier = supplier;
    }

    public Order(LocalDate dateOfOrder, Product product, Integer units, Supplier supplier) {
        this.dateOfOrder = dateOfOrder;
        this.product = product;
        this.units = units;
        this.supplier = supplier;
    }
}
