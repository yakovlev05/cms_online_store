package ru.yakovlev05.cms.cart.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yakovlev05.cms.cart.entity.Product;
import ru.yakovlev05.cms.cart.exception.BadRequestException;
import ru.yakovlev05.cms.cart.repository.ProductRepository;
import ru.yakovlev05.cms.cart.service.ProductService;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;


    @Override
    public Product getProductByUrlName(String urlName) {
        return productRepository.findByUrlName(urlName)
                .orElseThrow(() ->
                        new BadRequestException("Product with url name " + urlName + " not found"));
    }

    @Override
    public void createOrUpdate(Product event) {
        productRepository.save(event);
    }

    @Override
    public void deleteById(long productId) {
        productRepository.deleteById(productId);
    }
}
