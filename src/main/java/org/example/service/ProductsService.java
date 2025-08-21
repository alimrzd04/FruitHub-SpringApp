package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.ProductDto;
import org.example.model.Product;
import org.example.model.Status;
import org.example.repository.ProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductsService {

    private final ProductRepository productRepository;

    public List<Product> findAllProducts (String search, String sort, int page, int offset) {
        Sort.Direction direction = sort.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page,offset, Sort.by(direction, "name"));

        if(search != null && !search.isEmpty()){
            return productRepository.findByNameContainingIgnoreCase(search,pageable).getContent();

        }
        return productRepository.findAll(pageable).getContent();
    }

    public Product createProduct(ProductDto dto) {
        Status status = productRepository.findById(dto.getStatusId())
                .orElseThrow(() -> new RuntimeException("Status not found")).getStatus();

        Product product = Product.builder()
                .uuid(UUID.randomUUID())
                .name(dto.getName())
                .price(dto.getPrice())
                .stock_quantity(dto.getStockQuantity())
                .categoryId(dto.getCategoryId())
                .quantityId(dto.getQuantityId())
                .currencyId(dto.getCurrencyId())
                .status(status)
                .build();

        return productRepository.save(product);
    }

}
