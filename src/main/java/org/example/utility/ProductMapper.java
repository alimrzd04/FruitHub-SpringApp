package org.example.utility;

import org.example.dto.MapperDto;
import org.example.model.Products;

public class ProductMapper {

    public static MapperDto toDTO(Products product) {
        MapperDto dto = new MapperDto();
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setStockQuantity(product.getStock_quantity());

        return dto;
    }
}
