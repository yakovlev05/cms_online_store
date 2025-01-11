package ru.yakovlev05.cms.cart.service;

import ru.yakovlev05.cms.cart.dto.CreateCartDto;
import ru.yakovlev05.cms.cart.dto.ResponseCartDto;
import ru.yakovlev05.cms.cart.dto.UpdateCartDto;
import ru.yakovlev05.cms.core.security.UserDetailsImpl;

import java.util.List;

public interface CartService {
    List<ResponseCartDto> getCart(UserDetailsImpl userDetails);

    void deleteCartItem(long id, UserDetailsImpl userDetails);

    ResponseCartDto addCartItem(UserDetailsImpl userDetails, CreateCartDto requestCartDto);

    ResponseCartDto updateCartItem(UserDetailsImpl userDetails, UpdateCartDto requestCart, long id);

    boolean isInMyCart(UserDetailsImpl userDetails, String productUrl);
}
