package org.example.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CartResponseDto {
    private UUID productId;
    private String name;
    private Double price;
//    private int quantity;
}
