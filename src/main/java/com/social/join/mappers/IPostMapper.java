package com.social.join.mappers;

import com.social.join.dtos.PostCreateRequest;
import com.social.join.dtos.PostDTO;
import com.social.join.dtos.PostUpdateRequest;
import com.social.join.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IPostMapper {

    Post postDTOToPost(PostDTO postDTO);
    PostDTO postToPostDTO(Post post);
    Post postCreateRequestToPost(PostCreateRequest postCreateRequest);
    Post postUpdateRequestToPost(PostUpdateRequest postUpdateRequest);
    PostCreateRequest postDTOToPostCreateRequest(PostDTO postDTO);
    PostUpdateRequest postDTOToPostUpdateRequest(PostDTO postDTO);
}
