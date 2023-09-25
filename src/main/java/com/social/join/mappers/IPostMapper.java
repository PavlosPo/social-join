package com.social.join.mappers;

import com.social.join.dtos.PostDTO;
import com.social.join.entities.Post;
import org.mapstruct.Mapper;

@Mapper
public interface IPostMapper {

    Post postDTOToPost(PostDTO postDTO);
    PostDTO postToPostDTO(Post post);
}
