package org.example.controller;


import lombok.RequiredArgsConstructor;
import org.example.dto.ProductDto;
import org.example.model.Product;
import org.example.service.ProductsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductController {

    private final ProductsService productsService;

    @GetMapping("/products")
    public List<Product> findAllProducts(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "asc") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int offset
    ) {
        return productsService.findAllProducts(search, sort, page, offset);
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody ProductDto productDto) {
        Product savedProduct = productsService.createProduct(productDto);
        return ResponseEntity.ok(savedProduct);

    }
}
