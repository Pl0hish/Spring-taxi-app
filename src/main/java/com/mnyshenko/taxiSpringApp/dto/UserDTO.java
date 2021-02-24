package com.mnyshenko.taxiSpringApp.dto;

import lombok.*;

import javax.validation.constraints.*;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    @Size(min = 3, max = 20, message = "{firstName.error}")
    private String firstName;

    @Size(min = 3, max = 20, message = "{lastName.error}")
    private String lastName;

    @Email(message = "{email.error}")
    private String email;

    @Size(min = 6, max = 20, message = "{password.error}")
    private String password;
}