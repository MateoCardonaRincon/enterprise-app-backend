package com.mateocr.enterpriseapp.collections;

import com.mateocr.enterpriseapp.dto.ProductDTO;
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
    private ProductDTO product;
    private Integer units;

    public Order() {
    }

    public Order(String id, LocalDate dateOfOrder, ProductDTO product, Integer units) {
        this.id = id;
        this.dateOfOrder = dateOfOrder;
        this.product = product;
        this.units = units;
    }

    public Order(LocalDate dateOfOrder, ProductDTO product, Integer units) {
        this.dateOfOrder = dateOfOrder;
        this.product = product;
        this.units = units;
    }
}
