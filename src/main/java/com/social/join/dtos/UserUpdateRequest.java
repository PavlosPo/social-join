package com.social.join.dtos;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {
    @Id
    private Integer id;

    @NotNull
    @NotBlank
    private String firstname;

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

    private Integer version;

    // Getters and setters
}

