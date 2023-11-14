package com.social.join.mappers;

import com.social.join.dtos.CommentDTO;
import com.social.join.dtos.PostDTO;
import com.social.join.dtos.UserDTO;
import com.social.join.entities.Comment;
import com.social.join.entities.Post;
import com.social.join.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper
public interface ICommentMapper {
    ICommentMapper INSTANCE = Mappers.getMapper(ICommentMapper.class);

    default CommentDTO commentToCommentDTO(Comment comment) {
        Set<Object> mappedObjects = new HashSet<>();
        return commentToCommentDTOWithCyclePrevention(comment, mappedObjects);
    }

    default Comment commentDTOToComment(CommentDTO commentDTO) {
        // Implement this method if needed
        return null;
    }

    default CommentDTO commentToCommentDTOWithCyclePrevention(Comment comment, Set<Object> mappedObjects) {
        if (comment == null || mappedObjects.contains(comment)) {
            return null;
        }

        mappedObjects.add(comment);

        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setId(comment.getId());
        commentDTO.setPost(mapPostEntityToDTOWithCyclePrevention(comment.getPost(), mappedObjects));
        commentDTO.setContent(comment.getContent());
        commentDTO.setUserCreatedIt(mapUserEntityToDTOWithCyclePrevention(comment.getUserCreatedIt(), mappedObjects));
        commentDTO.setUsersWhoLikedThisComment(mapUserEntitiesToDTOsWithCyclePrevention(    comment.getUsersWhoLikedThisComment(), mappedObjects));
        commentDTO.setCreatedDate(comment.getCreatedDate());
        commentDTO.setUpdatedDate(comment.getUpdatedDate());

        return commentDTO;
    }

    default List<UserDTO> mapUserEntitiesToDTOsWithCyclePrevention(List<User> usersWhoLikedThisComment, Set<Object> mappedObjects) {
        if (usersWhoLikedThisComment == null) {
            return null;
        }

        return usersWhoLikedThisComment.stream()
                .map(user -> mapUserEntityToDTOWithCyclePrevention(user, mappedObjects))
                .toList();
    }

    default UserDTO mapUserEntityToDTOWithCyclePrevention(User userCreatedIt, Set<Object> mappedObjects) {
        if (userCreatedIt == null || mappedObjects.contains(userCreatedIt)) {
            return null;
        }

        mappedObjects.add(userCreatedIt);

        return UserDTO.builder()
                .username(userCreatedIt.getUsername())
                .lastname(userCreatedIt.getLastname())
                .password(userCreatedIt.getPassword())
                .email(userCreatedIt.getEmail())
                .firstname(userCreatedIt.getFirstname())
                .id(userCreatedIt.getId())
                .likedComments(null)
                .likedPosts(null)
                .build();
    }

    default PostDTO mapPostEntityToDTOWithCyclePrevention(Post post, Set<Object> mappedObjects) {
        if (post == null || mappedObjects.contains(post)) {
            return null;
        }

        mappedObjects.add(post);

        return PostDTO.builder()
                .id(post.getId())
                .comments(null)
                .content(post.getContent())
                .createdDate(post.getCreatedDate())
                .updateDate(post.getUpdateDate())
                .userCreated(mapUserEntityToDTOWithCyclePrevention(post.getUserCreated(), mappedObjects))
                .usersWhoLikedThisPost(mapUserEntitiesToDTOsWithCyclePrevention(post.getUsersWhoLikedThisPost(), mappedObjects))
                .comments(mapCommentsEntityToDTOWithCyclePrevention(post.getComments(), mappedObjects))
                .build();
    }

    default List<CommentDTO> mapCommentsEntityToDTOWithCyclePrevention(List<Comment> comments, Set<Object> mappedObjects) {
        if (comments == null) {
            return null;
        }

        return comments.stream()
                .map(comment -> commentToCommentDTOWithCyclePrevention(comment, mappedObjects))
                .toList();
    }


}
