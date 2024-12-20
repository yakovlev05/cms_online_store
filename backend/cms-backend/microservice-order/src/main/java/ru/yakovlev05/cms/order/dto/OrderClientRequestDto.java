package ru.yakovlev05.cms.order.dto;

import lombok.Data;
import ru.yakovlev05.cms.order.entity.PaymentType;
import ru.yakovlev05.cms.order.entity.ReceivingType;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderClientRequestDto {
    private List<Product> products;
    private CustomerInfo customerInfo;
    private RecipientInfo recipientInfo;
    private long communicationMethodId;
    private PaymentType paymentType;
    private String commentForRecipient;
    private String orderComment;
    private ReceivingInfo receivingInfo;


    @Data
    public class Product {
        private long id;
        private int count;
        private String name;
        private BigDecimal price;
    }

    @Data
    public class CustomerInfo {
        private String firstName;
        private String secondName;
        private String patronymic;
        private String phoneNumber;
    }

    @Data
    public class RecipientInfo {
        private String firstName;
        private String secondName;
        private String patronymic;
        private String phoneNumber;
    }

    @Data
    public class ReceivingInfo {
        private ReceivingType type;
        private long dateTimeReceivingInSeconds;
        private Address address;

        @Data
        public class Address {
            private String country;
            private String state;
            private String city;
            private String street;
            private String houseNumber;
            private String flatNumber;
        }
    }

    @Data
    public class PaymentInfo {
        private PaymentType paymentType;
    }
}
