package ru.yakovlev05.cms.cart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.yakovlev05.cms.cart.dto.CreateCartDto;
import ru.yakovlev05.cms.cart.dto.ResponseCartDto;
import ru.yakovlev05.cms.cart.dto.UpdateCartDto;
import ru.yakovlev05.cms.cart.service.CartService;
import ru.yakovlev05.cms.core.security.UserDetailsImpl;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping
    public List<ResponseCartDto> getCart(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return cartService.getCart(userDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteCartItem(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable long id) {
        cartService.deleteCartItem(id, userDetails);
    }

    @PostMapping
    public ResponseCartDto addCartItem(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody CreateCartDto requestCartDto) {
        return cartService.addCartItem(userDetails, requestCartDto);
    }

    @PutMapping("/{id}")
    public ResponseCartDto updateCartItem(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody UpdateCartDto requestCart,
            @PathVariable long id) {
        return cartService.updateCartItem(userDetails, requestCart, id);
    }

    @GetMapping("/{product-url}")
    public boolean inMyCart(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable(name = "product-url") String productUrl) {
        return cartService.isInMyCart(userDetails, productUrl);
    }
}
