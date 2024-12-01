package ru.yakovlev05.cms.cart.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yakovlev05.cms.cart.dto.CreateCartDto;
import ru.yakovlev05.cms.cart.dto.ProductDto;
import ru.yakovlev05.cms.cart.dto.ResponseCartDto;
import ru.yakovlev05.cms.cart.dto.UpdateCartDto;
import ru.yakovlev05.cms.cart.entity.Cart;
import ru.yakovlev05.cms.cart.entity.Product;
import ru.yakovlev05.cms.cart.entity.User;
import ru.yakovlev05.cms.cart.exception.BadRequestException;
import ru.yakovlev05.cms.cart.exception.ForbiddenException;
import ru.yakovlev05.cms.cart.repository.CartRepository;
import ru.yakovlev05.cms.cart.service.CartService;
import ru.yakovlev05.cms.cart.service.ProductService;
import ru.yakovlev05.cms.cart.service.UserService;
import ru.yakovlev05.cms.core.security.UserDetailsImpl;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final UserService userService;
    private final ProductService productService;

    @Override
    public List<ResponseCartDto> getCart(UserDetailsImpl userDetails) {
        User user = userService.getUser(userDetails.getId());

        return user.getCarts().stream()
                .map(this::fillResponseCartDto)
                .toList();
    }

    @Override
    public void deleteCartItem(long id, UserDetailsImpl userDetails) {
        User user = userService.getUser(userDetails.getId());

        Cart cart = this.getById(id);

        if (user.getId() != cart.getUser().getId()) {
            throw new ForbiddenException("Forbidden");
        }

        cartRepository.delete(cart);
    }

    @Override
    public ResponseCartDto addCartItem(UserDetailsImpl userDetails, CreateCartDto requestCartDto) {
        User user = userService.getUser(userDetails.getId());
        if (user.getCarts().stream()
                .anyMatch(x -> x.getProduct()
                        .getUrlName()
                        .equals(requestCartDto.getProductUrlName()))) {
            throw new BadRequestException("Cart already exists");
        }

        Product product = productService.getProductByUrlName(requestCartDto.getProductUrlName());

        Cart cart = Cart.builder()
                .isSelected(false)
                .count(1)
                .user(user)
                .product(product)
                .build();

        cartRepository.save(cart);

        return fillResponseCartDto(cart);
    }

    @Override
    public ResponseCartDto updateCartItem(UserDetailsImpl userDetails, UpdateCartDto requestCart, long id) {
        User user = userService.getUser(userDetails.getId());
        Cart cart = this.getById(id);

        if (cart.getUser().getId() != user.getId()) {
            throw new ForbiddenException("Forbidden");
        }

        cart.setCount(requestCart.getCount());

        cartRepository.save(cart);

        return fillResponseCartDto(cart);
    }

    private Cart getById(long id) {
        return cartRepository.findById(id)
                .orElseThrow(() ->
                        new BadRequestException("Cart item with id " + id + " not found"));
    }

    private ProductDto fillProductDto(Product product) {
        return ProductDto.builder()
                .name(product.getName())
                .urlName(product.getUrlName())
                .mainPhotoUrl(product.getMainPhotoUrl())
                .price(product.getPrice())
                .discount(product.getDiscount())
                .isAvailable(product.isAvailable())
                .build();
    }

    private ResponseCartDto fillResponseCartDto(Cart cart) {
        return ResponseCartDto.builder()
                .id(cart.getId())
                .isSelected(cart.isSelected())
                .count(cart.getCount())
                .product(fillProductDto(cart.getProduct()))
                .build();
    }
}
