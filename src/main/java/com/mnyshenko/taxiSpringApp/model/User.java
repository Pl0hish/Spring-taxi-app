package com.mnyshenko.taxiSpringApp.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 20)
    private String firstName;

    @NotBlank
    @Size(min = 3, max = 20)
    private String lastName;

    @Email
    private String email;

    @NotNull
    @Min(value = 0)
    private Integer spentMoney;

    @ToString.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Order> orders;

    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
