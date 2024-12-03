package ru.yakovlev05.cms.cart.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.yakovlev05.cms.cart.entity.Product;
import ru.yakovlev05.cms.cart.service.ProductService;
import ru.yakovlev05.cms.core.event.ProductEvent;

@RequiredArgsConstructor
@Component
@Slf4j
@KafkaListener(topics = "product")
public class ProductEventListener {

    private final ProductService productService;

    @KafkaHandler
    public void handleProductEvent(ProductEvent event) {
        switch (event.getEventType()) {
            case CREATE, UPDATE:
                handleProductCreateOrUpdateEvent(event);
                break;
            case DELETE:
                handleProductDeleteEvent(event);
                break;
            default:
                break;
        }
    }

    private void handleProductCreateOrUpdateEvent(ProductEvent event) {
        Product product = Product.builder()
                .id(event.getId())
                .name(event.getName())
                .urlName(event.getUrlName())
                .mainPhotoUrl(event.getMainPhotoUrl())
                .price(event.getPrice())
                .discount(event.getPriceDiscount())
                .isAvailable(event.isAvailable())
                .build();

        productService.createOrUpdate(product);
    }

    private void handleProductDeleteEvent(ProductEvent event) {
        productService.deleteById(event.getId());
    }
}
