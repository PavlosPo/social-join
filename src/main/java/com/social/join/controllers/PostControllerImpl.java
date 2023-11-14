package com.social.join.controllers;

import com.social.join.dtos.PostDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/posts")
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
