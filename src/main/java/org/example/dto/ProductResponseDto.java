package org.example.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDto {
    private String name;
    private Double price;
    private int stockQuantity;
}
