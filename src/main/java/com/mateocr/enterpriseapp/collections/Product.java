package com.mateocr.enterpriseapp.collections;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("product")
public class Product {

    @Id
    private String id;
    private String name;
    private String description;
    private Integer stock;
    private Integer minimumAmount;
    private Integer maximumAmount;
    private Double price;
    private Supplier supplier;
    private Integer units;

    public Product() {
    }

    public Product(String id, String name, String description, Integer stock, Integer minimumAmount, Integer maximumAmount, Double price, Supplier supplier, Integer units) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.minimumAmount = minimumAmount;
        this.maximumAmount = maximumAmount;
        this.price = price;
        this.supplier = supplier;
        this.units = units;
    }

    public Product(String name, String description, Integer stock, Integer minimumAmount, Integer maximumAmount, Double price, Supplier supplier, Integer units) {
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.minimumAmount = minimumAmount;
        this.maximumAmount = maximumAmount;
        this.price = price;
        this.supplier = supplier;
        this.units = units;
    }
}
