package com.social.join.dtos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    @NotNull
    @NotBlank
    private Integer id;

    @Column(name = "CONTENT", length = 512, nullable = false, updatable = true, unique = false)
    @NotNull
    private String content;

    @OneToOne
    @Column(name = "POST", unique = true, nullable = false)
    @NotNull
    private PostDTO post;

    @ManyToMany
    private Set<HashtagDTO> hashtag;

    @OneToOne
    @Column(name = "USER_CREATED_IT")
    @NotNull
    private UserDTO userCreatedIt;

    @OneToMany
    @Column(name = "USERS_LIKED_IT")
    private Set<UserDTO> usersLikedIt;

    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
