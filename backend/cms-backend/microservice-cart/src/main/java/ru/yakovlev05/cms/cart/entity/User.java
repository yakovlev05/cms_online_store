package ru.yakovlev05.cms.cart.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "\"user\"")
public class User {

    @Id
    private long id;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Cart> carts;
}
