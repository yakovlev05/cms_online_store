package ru.yakovlev05.cms.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "receiving_info")
public class ReceivingInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "receiving_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ReceivingType receivingType;

    @Column(name = "receiving_date_time", nullable = false)
    private LocalDateTime receivingDateTime;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;
}
