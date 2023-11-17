package com.social.join.dtos;


import com.social.join.entities.Comment;
import jakarta.annotation.Nullable;
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
public class PostDTO {

    private Integer id;

    @NotNull
    private String content;

    @Builder.Default
    private List<UserDTO> usersWhoLikedThisPost = new ArrayList<>();

    @NotNull
    private UserDTO userCreated;

    @Nullable
    @Builder.Default
    private List<CommentDTO> comments = new ArrayList<>();

    @NotNull
    private LocalDateTime createdDate;

    @NotNull
    private LocalDateTime updatedDate;

    public boolean addComment(CommentDTO comment) {
        if (comment == null) {
            return false;  // Comment is null, not added
        }

        if (comments.contains(comment)) {
            return false;  // Comment already exists, not added
        }

        this.comments.add(comment);
        comment.setPost(this);
        return true;  // Comment added
    }
}
