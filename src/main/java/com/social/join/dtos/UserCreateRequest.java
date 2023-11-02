package com.social.join.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreateRequest {
    private  Integer id;

    @NotNull
    @NotBlank
    private String firstname;

    @NotNull
    @NotBlank
    private String lastname;

    @NotNull
    @NotBlank
    private String username;

    @NotNull
    @NotBlank
    @Email(message = "Email Error")
    private String email;

    @NotNull
    @NotBlank
    @Size(min = 6)
    private String password;

}

