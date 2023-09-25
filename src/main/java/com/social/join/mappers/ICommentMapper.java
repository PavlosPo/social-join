package com.social.join.mappers;

import com.social.join.dtos.CommentDTO;
import com.social.join.entities.Comment;
import org.mapstruct.Mapper;

@Mapper
public interface ICommentMapper {

    Comment commentDTOToComment(CommentDTO commentDTO);
    CommentDTO commentToCommentDTO(Comment comment);
}
