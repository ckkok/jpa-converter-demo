package com.example.demo.models;

import com.example.demo.utils.LocalDateWithoutDay;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email_address")
    private String emailAddress;

    @Convert(converter = LocalDateWithoutDay.EntityFieldConverter.class)
    @Column(name = "birth_month")
    private LocalDate birthMonth;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_items",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "inventory_id")
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Item> items;
}
