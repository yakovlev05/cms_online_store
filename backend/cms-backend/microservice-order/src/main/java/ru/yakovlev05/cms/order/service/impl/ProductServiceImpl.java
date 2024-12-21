package ru.yakovlev05.cms.order.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yakovlev05.cms.order.entity.Order;
import ru.yakovlev05.cms.order.entity.Product;
import ru.yakovlev05.cms.order.repository.ProductRepository;
import ru.yakovlev05.cms.order.service.ProductService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product with id " + id + " not found"));
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public List<Product> getProductsByOrder(Order order) {
        return productRepository.findAllByOrder(order);
    }
}
