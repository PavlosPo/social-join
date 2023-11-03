package com.social.join.controllers;

import com.social.join.dtos.PostDTO;
import com.social.join.dtos.UserCreateRequest;
import com.social.join.dtos.UserDTO;
import com.social.join.dtos.UserUpdateRequest;
import com.social.join.entities.Post;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public interface IPostController {

    @GetMapping("/posts")
    ResponseEntity<List<PostDTO>> getAllPosts();

    @GetMapping("/posts/{id}")
    ResponseEntity<PostDTO> getPostById(@PathVariable Integer id);

    @PostMapping("/posts")
    ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO);

    @PutMapping("/posts/{id}")
    ResponseEntity<PostDTO> updatePost(@PathVariable Integer id, @RequestBody PostDTO postDTO);

    @DeleteMapping("/posts/{id}")
    ResponseEntity<Boolean> deletePost(@PathVariable Integer id);


}
