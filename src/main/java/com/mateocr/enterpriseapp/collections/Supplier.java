package com.mateocr.enterpriseapp.collections;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Supplier {

    @Id
    private String id;
    private String name;
    private String phoneNumber;
    private String personalId;

    public Supplier() {
    }

    public Supplier(String id, String name, String phoneNumber, String personalId) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.personalId = personalId;
    }

    public Supplier(String name, String phoneNumber, String personalId) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.personalId = personalId;
    }
}
