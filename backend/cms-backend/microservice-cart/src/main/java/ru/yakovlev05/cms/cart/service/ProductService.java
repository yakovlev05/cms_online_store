package ru.yakovlev05.cms.cart.service;

import ru.yakovlev05.cms.cart.entity.Product;

public interface ProductService {
    Product getProductByUrlName(String urlName);
}
