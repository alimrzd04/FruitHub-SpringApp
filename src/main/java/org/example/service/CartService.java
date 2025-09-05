package org.example.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.CartResponseDto;
import org.example.model.Cart;
import org.example.model.Products;
import org.example.model.Status;
import org.example.model.Users;
import org.example.repository.CartRepository;
import org.example.repository.ProductRepository;
import org.example.repository.StatusRepository;
import org.example.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final StatusRepository statusRepository;

    public String addToCart(String userEmail, UUID productId) {
        Users user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + userEmail));

        Products product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        Status activeStatus = statusRepository.findByName("ACTIVE")
                .orElseThrow(() -> new RuntimeException("Active status not found"));

        Optional<Cart> existingCart = cartRepository.findByUserAndProduct(user.getUuid(), productId);

        if (existingCart.isPresent()) {
            Cart cart = existingCart.get();
            cart.setCount(cart.getCount() + 1);
            cartRepository.save(cart);
            return "Product quantity updated in cart";
        } else {
            Cart newCart = Cart.builder()
                    .users(user)
                    .products(product)
                    .count(1)
                    .status(activeStatus)
                    .build();

            cartRepository.save(newCart);
            return "Product added to cart successfully";
        }
    }

    public Integer getCartProductCount(String userEmail) {
        Users user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + userEmail));

        return cartRepository.getTotalProductCount(user.getUuid());
    }

    public Page<CartResponseDto> getCartItems(String userEmail, int page, int size) {
        Users user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + userEmail));

        Page<Cart> cartPage = cartRepository.findAllByUsers_Uuid(user.getUuid(), PageRequest.of(page, size));

        return cartPage.map(cart -> {
            CartResponseDto dto = new CartResponseDto();
            dto.setProductId(cart.getProducts().getUuid());
            dto.setName(cart.getProducts().getName());
            dto.setPrice(cart.getProducts().getPrice());
            dto.setCount(cart.getCount());
            return dto;
        });
    }

    public String increaseProduct(String userEmail, UUID productId){
        Users user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + userEmail));
        Cart cart = cartRepository.findByUserAndProduct(user.getUuid(), productId)
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));

        cart.setCount(cart.getCount() + 1);
        cartRepository.save(cart);
        return "Product quantity increased in cart";
    }

    public String decreaseProduct(String userEmail, UUID productId){
        Users user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + userEmail));
        Cart cart = cartRepository.findByUserAndProduct(user.getUuid(), productId)
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));

        if (cart.getCount() > 1) {
            cart.setCount(cart.getCount() - 1);
            cartRepository.save(cart);
            return "Product quantity decreased in cart";
        } else {
            cartRepository.delete(cart);
            return "Product removed from cart";
        }
    }

    public String deleteProductFromCart(String userEmail, UUID productId) {
        Users user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + userEmail));
        Cart cart = cartRepository.findByUserAndProduct(user.getUuid(), productId)
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));
        cartRepository.delete(cart);
        return "Product removed from cart";
    }
}