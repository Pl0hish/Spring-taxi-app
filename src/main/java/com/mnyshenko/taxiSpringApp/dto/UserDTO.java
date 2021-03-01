package com.mnyshenko.taxiSpringApp.dto;

import lombok.*;

import javax.validation.constraints.*;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    @Size(min = 3, max = 20, message = "{firstName.error}")
    @NotBlank
    private String firstName;

    @Size(min = 3, max = 20, message = "{lastName.error}")
    @NotBlank
    private String lastName;

    @Email(message = "{email.error}")
    @NotBlank
    private String email;

    @Size(min = 6, max = 20, message = "{password.error}")
    @NotBlank
    private String password;
}