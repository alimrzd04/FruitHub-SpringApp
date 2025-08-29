package org.example.controller;


import java.util.List;
import org.example.dto.ProductDto;
import org.example.dto.ProductResponseDto;
import org.example.model.Products;
import java.util.stream.Collectors;
//import org.example.utility.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.example.service.ProductsService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductsService productsService;

    @GetMapping
    public Page<ProductResponseDto> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search) {

        return productsService.getAllProducts(page, size, search);
    }

    @PostMapping("/create")
    public ResponseEntity<Products> createProduct(@RequestBody ProductDto productDto) {
        Products savedProducts = productsService.createProduct(productDto);
        return ResponseEntity.ok(savedProducts);

    }
}
