package com.social.join.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostUpdateRequest {

    @NotNull
    private Integer id;

    @NotNull
    private String content;

    @NotNull
    private UserDTO userCreated;

    @Builder.Default
    private List<CommentDTO> comments = null;

    @Builder.Default
    private List<UserDTO> usersWhoLikedThisPost = new ArrayList<>();

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
