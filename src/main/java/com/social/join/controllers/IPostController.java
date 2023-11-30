package com.social.join.controllers;

import com.social.join.dtos.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface IPostController {

    ResponseEntity<List<PostDTO>> getAllPosts();

    ResponseEntity<PostDTO> getPostById(@PathVariable Integer id);

    ResponseEntity<PostDTO> createPost(@RequestBody PostCreateRequest postCreateRequest);

    ResponseEntity<PostDTO> updatePost(@PathVariable Integer id, @RequestBody PostUpdateRequest postDTO);

    ResponseEntity<Boolean> deletePost(@PathVariable Integer id);


}
