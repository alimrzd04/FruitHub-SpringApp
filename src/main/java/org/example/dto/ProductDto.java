package org.example.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class ProductDto {
    private String name;
    private Double price;
    private Integer stockQuantity;
    private Integer categoryId;
    private Integer quantityId;
    private Integer currencyId;
    private UUID statusId;
}
