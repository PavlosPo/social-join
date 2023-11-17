package com.social.join.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Builder
@Data
@Entity
@Table(name = "USERS")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Integer id;

//    @NotNull
//    @NotBlank
    private String firstname;

//    @NotBlank
    private String lastname;

//    @NotNull
//    @NotBlank
    private String username;

    @Builder.Default
    @ManyToMany
    @JoinTable(
            name = "USER_FRIENDS",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "FRIEND_ID")
    )
    private List<User> friends = new ArrayList<>();

//    @NotNull
//    @NotBlank
    @Email(message = "Email Error")
    private String email;

//    @NotNull
//    @NotBlank
    @Size(min = 6)
    private String password;

    @Builder.Default
    @ManyToMany
    @JoinTable(
            name = "USER_LIKES_POSTS",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "POST_ID")
    )
    private List<Post> likedPosts = new ArrayList<>();

    @Builder.Default
    @ManyToMany
    @JoinTable(
            name = "USER_LIKES_COMMENTS",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "COMMENT_ID")
    )
    private List<Comment> likedComments = new ArrayList<>();
}
