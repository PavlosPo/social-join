package com.social.join.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;



@Builder
@Data
@Entity
@Table(name = "USERS")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private Integer id;

    @Version
    private Integer version;

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


//    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "usersLikedIt")
//    private List<Post> likedPosts;
//
//    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "usersLikedIt")
//    private List<Comment> likedComments;
}
