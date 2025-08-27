package org.example.dto;

import org.example.model.Products;

public class ProductMapper {

    public static ProductDto toDTO(Products product) {
        ProductDto dto = new ProductDto();
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setStockQuantity(product.getStock_quantity());
        dto.setStatusId(product.getStatus() != null ? product.getStatus().getUuid() : null);
        dto.setCategoryId(product.getCategory() != null ? product.getCategory().getUuid() : null);
        dto.setCurrencyId(product.getCurrency() != null ? product.getCurrency().getUuid() : null);
        dto.setQuantityId(product.getQuantities() != null ? product.getQuantities().getUuid() : null);

        return dto;
    }
}
