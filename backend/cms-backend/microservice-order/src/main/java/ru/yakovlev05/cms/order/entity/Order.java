package ru.yakovlev05.cms.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "\"order\"")
public class Order {

    @Id
    private String id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Product> products;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private CustomerInfo customerInfo;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recipient_id", referencedColumnName = "id", nullable = false)
    private RecipientInfo recipientInfo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "receiving_id", referencedColumnName = "id", nullable = false)
    private ReceivingInfo receivingInfo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "communication_method_id", referencedColumnName = "id", nullable = false)
    private CommunicationMethod communicationMethod;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_info_id", referencedColumnName = "id", nullable = false)
    private PaymentInfo paymentInfo;

    @Column(name = "products_cost", nullable = false, scale = 10, precision = 2)
    private BigDecimal productsCost;

    @Column(name = "delivery_cost", nullable = false, scale = 10, precision = 2)
    private BigDecimal deliveryCost;

    @Column(name = "order_Comment", columnDefinition = "TEXT", length = 500)
    private String orderComment;

    @Column(name = "comment_for_recipient", columnDefinition = "TEXT", length = 100)
    private String commentForRecipient;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "is_completed", nullable = false)
    private boolean isCompleted;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
