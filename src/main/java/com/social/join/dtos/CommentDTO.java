package com.social.join.dtos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private Integer id;

    @NotNull
    private String content;

    @NotNull
    private PostDTO post;

//    private List<HashtagDTO> hashtag;

    @NotNull
    private UserDTO userCreatedIt;

    @Builder.Default
    private List<UserDTO> usersWhoLikedThisComment = new ArrayList<>();

    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
