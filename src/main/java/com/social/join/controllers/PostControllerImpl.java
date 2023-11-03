package com.social.join.controllers;

import com.social.join.dtos.PostDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class PostControllerImpl implements IPostController{
    @Override
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        return null;
    }

    @Override
    public ResponseEntity<PostDTO> getPostById(Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<PostDTO> createPost(PostDTO postDTO) {
        return null;
    }

    @Override
    public ResponseEntity<PostDTO> updatePost(Integer id, PostDTO postDTO) {
        return null;
    }

    @Override
    public ResponseEntity<Boolean> deletePost(Integer id) {
        return null;
    }
}
