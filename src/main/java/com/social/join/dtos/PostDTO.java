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
public class PostDTO {

    private Integer id;

    @NotNull
    @NotBlank
    private String content;

    private List<UserDTO> usersWhoLikedThisPost = new ArrayList<>();

    private UserDTO userCreated;

    @NotNull
    private LocalDateTime createdDate;

    @NotNull
    private LocalDateTime updateDate;

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
