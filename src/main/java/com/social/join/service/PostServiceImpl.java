package com.social.join.service;

import com.social.join.dtos.CommentDTO;
import com.social.join.dtos.HashtagDTO;
import com.social.join.dtos.PostDTO;
import com.social.join.dtos.UserDTO;
import com.social.join.entities.User;

import java.util.List;
import java.util.Optional;

public class PostServiceImpl implements IPostService {
    @Override
    public PostDTO createPost(User user, PostDTO postDTO) {
        return null;
    }

    @Override
    public List<PostDTO> getPostsByUserId(int id) {
        return null;
    }

    @Override
    public Optional<PostDTO> getPostById(int id) {
        return Optional.empty();
    }

    @Override
    public Boolean deletePostById(int id) {
        return null;
    }

    @Override
    public PostDTO updatePostById(int id, PostDTO postDTO) {
        return null;
    }

    @Override
    public List<CommentDTO> getAllTheCommentsByPostId(int id) {
        return null;
    }

    @Override
    public List<HashtagDTO> getAllTheHashtagsByPostId(int id) {
        return null;
    }

    @Override
    public List<UserDTO> getUsersThatLikedByPostId(int id) {
        return null;
    }
}
