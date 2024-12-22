package ru.yakovlev05.cms.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yakovlev05.cms.order.entity.OrderStatus;
import ru.yakovlev05.cms.order.entity.PaymentStatus;
import ru.yakovlev05.cms.order.entity.PaymentType;
import ru.yakovlev05.cms.order.entity.ReceivingType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderInfoClientResponseDto {
    private String orderId;
    private List<Product> products;
    private UserInfo customerInfo;
    private UserInfo recipientInfo;
    private PaymentInfo paymentInfo;
    private ReceivingInfo receivingInfo;
    private CommunicationMethod communicationMethod;
    private OrderStatus orderStatus;
    private LocalDateTime createdAt;
    private boolean isCompleted;
    private String orderComment;
    private String commentForRecipient;
    private BigDecimal deliveryCost;
    private BigDecimal productsCost;


    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Product {
        private long productId;
        private String name;
        private int count;
        private BigDecimal price;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class UserInfo {
        private String firstName;
        private String secondName;
        private String patronymic;
        private String phoneNumber;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class PaymentInfo {
        private PaymentType paymentType;
        private BigDecimal finalSum;
        private PaymentStatus paymentStatus;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class ReceivingInfo {
        private ReceivingType receivingType;
        private AddressInfo address;


        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        @Data
        public static class AddressInfo {
            private String country;
            private String state;
            private String city;
            private String street;
            private String houseNumber;
            private String flatNumber;
        }
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class CommunicationMethod {
        private long id;
        private String name;
    }
}
