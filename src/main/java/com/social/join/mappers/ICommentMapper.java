package com.social.join.mappers;

import com.social.join.dtos.CommentCreateRequest;
import com.social.join.dtos.CommentDTO;
import com.social.join.dtos.CommentUpdateRequest;
import com.social.join.entities.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ICommentMapper {

    Comment commentDTOToComment(CommentDTO commentDTO);
    CommentDTO commentToCommentDTO(Comment comment);
    Comment commentCreateRequestToComment(CommentCreateRequest commentCreateRequest);
    Comment commentUpdateRequestToComment(CommentUpdateRequest commentUpdateRequest);
    CommentCreateRequest commentDTOToCommentCreateRequest(CommentDTO commentDTO);
}
