package com.mateocr.enterpriseapp.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SupplierDTO {

    private String id;
    @NotBlank
    private String name;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private String notes;
    @NotBlank
    private String personalId;
}
