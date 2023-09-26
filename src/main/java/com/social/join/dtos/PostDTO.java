package com.social.join.dtos;


import jakarta.annotation.Nullable;
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
    @NotBlank
    @NotNull
    private Integer id;

    @NotNull
    @NotBlank
    private String content;

    @Nullable
    private Set<UserDTO> likedByUsers;

    @NotNull
    @NotBlank
    private UserDTO userCreated;

    @Nullable
    private Set<CommentDTO> comments;

//    @Nullable
//    private Set<HashtagDTO> hashtags;

    @NotNull
    private LocalDateTime createdDate;

    @NotNull
    private LocalDateTime updatedDate;
}
