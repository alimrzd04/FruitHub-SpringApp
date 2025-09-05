package org.example.controller;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.example.dto.CartResponseDto;
import org.example.service.CartService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@RestController
@RequestMapping("api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<Page<CartResponseDto>> getCartItems(
            Authentication authentication,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        String userEmail = authentication.getName();
        return ResponseEntity.ok(cartService.getCartItems(userEmail, page, size));
    }

    @PostMapping("/{productId}")
    public ResponseEntity<String> addProductToCart(@PathVariable UUID productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        String result = cartService.addToCart(userEmail, productId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getCartCount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        Integer count = cartService.getCartProductCount(userEmail);
        return ResponseEntity.ok(count);
    }

    @PatchMapping("/increase/{cartId}")
    public ResponseEntity<String> increaseProductCount(@PathVariable UUID cartId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        String result = cartService.increaseProduct(userEmail, cartId);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/decrease/{cartId}")
    public ResponseEntity<String> decreaseProductCount(@PathVariable UUID cartId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        String result = cartService.decreaseProduct(userEmail, cartId);
        return ResponseEntity.ok(result);
    }

 @DeleteMapping("/{cartId}")
    public ResponseEntity<String> removeProductFromCart(@PathVariable UUID cartId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        String result = cartService.deleteProductFromCart(userEmail, cartId);
        return ResponseEntity.ok(result);
    }
}