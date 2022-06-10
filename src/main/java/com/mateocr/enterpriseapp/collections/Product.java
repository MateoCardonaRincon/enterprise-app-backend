package com.mateocr.enterpriseapp.collections;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.security.Provider;

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
    private Provider provider;

    public Product() {
    }

    public Product(String id,
                   String name,
                   String description,
                   Integer stock,
                   Integer minimumAmount,
                   Integer maximumAmount,
                   Double price,
                   Provider provider) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.minimumAmount = minimumAmount;
        this.maximumAmount = maximumAmount;
        this.price = price;
        this.provider = provider;
    }

    public Product(String name,
                   String description,
                   Integer stock,
                   Integer minimumAmount,
                   Integer maximumAmount,
                   Double price,
                   Provider provider) {
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.minimumAmount = minimumAmount;
        this.maximumAmount = maximumAmount;
        this.price = price;
        this.provider = provider;
    }
}
