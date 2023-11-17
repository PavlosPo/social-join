package com.social.join.dtos;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentCreateRequest {

    @NotNull
    private String content;

    @NotNull
    private PostDTO post;

//    @Builder.Default
//    private List<HashtagDTO> hashtag = new ArrayList<>();

    @NotNull
    private UserDTO userCreatedIt;
}
