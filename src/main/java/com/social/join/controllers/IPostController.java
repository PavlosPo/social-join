package com.social.join.controllers;

import com.social.join.dtos.PostDTO;
import com.social.join.dtos.UserCreateRequest;
import com.social.join.dtos.UserDTO;
import com.social.join.dtos.UserUpdateRequest;
import com.social.join.entities.Post;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface IPostController {

    ResponseEntity<List<PostDTO>> getAllPosts();

    ResponseEntity<PostDTO> getPostById(@PathVariable Integer id);

    ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO);

    ResponseEntity<PostDTO> updatePost(@PathVariable Integer id, @RequestBody PostDTO postDTO);

    ResponseEntity<Boolean> deletePost(@PathVariable Integer id);


}
