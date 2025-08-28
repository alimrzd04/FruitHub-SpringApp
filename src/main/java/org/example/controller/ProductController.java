package org.example.controller;


import java.util.List;
import org.example.dto.ProductDto;
import org.example.model.Products;
import java.util.stream.Collectors;
import org.example.utility.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.example.service.ProductsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductsService productsService;


    @GetMapping
    public List<Object> getAllProducts() {
        List<Products> products = productsService.findAllProducts(null, "asc", 0, 30);
        return products.stream()
                .map(product -> ProductMapper.toDTO(product))
                .collect(Collectors.toList());
    }

    @PostMapping("/create")
    public ResponseEntity<Products> createProduct(@RequestBody ProductDto productDto) {
        Products savedProducts = productsService.createProduct(productDto);
        return ResponseEntity.ok(savedProducts);

    }
}
