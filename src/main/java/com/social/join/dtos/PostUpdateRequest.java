package com.social.join.dtos;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdateRequest {

    private Integer id;

    @NotNull
    @NotBlank
    private String content;


    private List<UserDTO> usersWhoLikedThisPost = new ArrayList<>();
}
