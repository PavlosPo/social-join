package com.social.join.dtos;

import com.social.join.entities.Comment;
import com.social.join.entities.Post;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

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

    private Set<Post> likedPosts = new HashSet<>();

    private Set<Comment> likedComments = new HashSet<>();
}
