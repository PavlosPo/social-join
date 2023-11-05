package com.social.join.dtos;


import com.social.join.entities.Comment;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
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
    @NotBlank
    private String content;

    @Nullable
    private List<UserDTO> usersWhoLikedThisPost;

    @NotNull
    @NotBlank
    private UserDTO userCreated;

    @Nullable
    private List<CommentDTO> comments;

//    @Nullable
//    private Set<HashtagDTO> hashtags;

    @NotNull
    private LocalDateTime createdDate;

    @NotNull
    private LocalDateTime updatedDate;

//    public boolean setComment(CommentDTO comment) {
//        if (comment == null) {
//            return false;  // Comment is null, not added
//        }
//
//        if (comments.contains(comment)) {
//            return false;  // Comment already exists, not added
//        }
//
//        this.comments.add(comment);
//        comment.setPost(this);
//        return true;  // Comment added
//    }
}
