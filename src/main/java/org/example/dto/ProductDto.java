package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class ProductDto {
    private String name;
    private Double price;
    private int stockQuantity;

    private UUID  statusId;
    private UUID  categoryId;
    private UUID  currencyId;
    private UUID  quantityId;
}
