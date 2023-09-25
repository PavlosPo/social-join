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
public class PostDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private Integer id;

    @NotNull
    @NotBlank
    @Column(name = "CONTENT", length = 512, nullable = false, updatable = true, unique = false)
    private String content;

    @OneToMany
    private Set<UserDTO> usersLikedIt;

    @NotNull
    @NotBlank
    @OneToOne
    private UserDTO userCreated;

    @OneToMany
    private Set<CommentDTO> comments;

    @OneToMany
    private Set<HashtagDTO> hashtags;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

}
