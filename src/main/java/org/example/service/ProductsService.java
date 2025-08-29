package org.example.service;

import java.util.List;

import org.example.dto.ProductResponseDto;
import org.example.model.*;
import org.example.repository.*;
import org.example.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

@Service
@RequiredArgsConstructor
public class ProductsService {

    private final ProductRepository productRepository;
    private final StatusRepository statusRepository;
    private final QuantityRepository quantityRepository;
    private final CurrenciesRepository currenciesRepository;
    private final CategoriesRepository categoriesRepository;

    public Page<ProductResponseDto> getAllProducts(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Products> productPage;
        if (search != null && !search.isEmpty()) {
            productPage = productRepository.findByNameContainingIgnoreCase(search, pageable);
        } else {
            productPage = productRepository.findAll(pageable);
        }

        return productPage.map(product -> {
            ProductResponseDto dto = new ProductResponseDto();
            dto.setName(product.getName());
            dto.setPrice(product.getPrice());
            dto.setStockQuantity(product.getStock_quantity());
            return dto;
        });
    }

    public Products createProduct(ProductDto dto) {

        Status status = statusRepository.findById(dto.getStatusId())
                .orElseThrow(() -> new RuntimeException("Status not found"));

        Categories category = categoriesRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Currencies currency = currenciesRepository.findById(dto.getCurrencyId())
                .orElseThrow(() -> new RuntimeException("Currency not found"));

        Quantities quantities = quantityRepository.findById(dto.getQuantityId())
                .orElseThrow(() -> new RuntimeException("Quantity not found"));

        Products products = Products.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .stock_quantity(dto.getStockQuantity())
                .category(category)
                .currency(currency)
                .quantities(quantities)
                .status(status)
                .build();
        return productRepository.save(products);
    }
}
