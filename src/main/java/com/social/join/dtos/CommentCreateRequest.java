package com.social.join.dtos;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CommentCreateRequest {
    @Id
    @NotNull
    @NotBlank
    private Integer id;

    @NotNull
    private String content;

    @NotNull
    private PostDTO post;

    @Builder.Default
    private List<HashtagDTO> hashtag = new ArrayList<>();

    @NotNull
    private UserDTO userCreatedIt;
}
