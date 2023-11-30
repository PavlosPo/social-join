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
public class PostCreateRequest {

    @NotNull
    @NotBlank
    private String content;

    @NotNull
    private UserDTO userCreated;

    private LocalDateTime createdDate;
}
