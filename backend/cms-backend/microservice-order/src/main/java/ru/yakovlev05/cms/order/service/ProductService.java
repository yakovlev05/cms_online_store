package ru.yakovlev05.cms.order.service;

import ru.yakovlev05.cms.order.entity.Order;
import ru.yakovlev05.cms.order.entity.Product;

import java.util.List;

public interface ProductService {
    Product getById(Long id);
    void save(Product product);
    List<Product> getProductsByOrder(Order order);
}
