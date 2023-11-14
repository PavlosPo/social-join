package com.social.join.dtos;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CommentUpdateRequest {
    @NotNull
    @NotBlank
    private Integer id;

    @NotNull
    private String content;

    @Builder.Default
    private List<HashtagDTO> hashtag = new ArrayList<>();

    @Builder.Default
    private List<UserDTO> usersWhoLikedThisComment = new ArrayList<>();

    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
