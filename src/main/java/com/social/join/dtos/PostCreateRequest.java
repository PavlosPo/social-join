package com.social.join.dtos;

import com.social.join.entities.Comment;
import com.social.join.entities.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostCreateRequest {

    @NotNull
    private String content;

    @NotNull
    private UserDTO userCreated;

    private LocalDateTime createdDate;


}
