package com.social.join.controllers;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.social.join.dtos.PostCreateRequest;
import com.social.join.dtos.PostDTO;
import com.social.join.dtos.PostUpdateRequest;
import com.social.join.mappers.IPostMapper;
import com.social.join.repositories.IPostRepository;
import com.social.join.service.IPostService;
import jakarta.validation.Valid;
import jdk.jfr.ContentType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/posts")
public class PostControllerImpl implements IPostController{


    private final IPostRepository postRepository;
    private final IPostService postService;
    private final IPostMapper postMapper;

    @Override
    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        return ResponseEntity.ok().body(
                postRepository.findAll().stream().map(postMapper::postToPostDTO)
                        .toList());
    }

    @Override
    @GetMapping("/{post_id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable("post_id") Integer id) {
        PostDTO postDTO = postService.getPostById(id)
                .orElseThrow(() -> new NotFoundException("The Post Does not Exists"));
        return ResponseEntity.ok(postDTO);
    }

    @Override
    @PostMapping()
    public ResponseEntity<PostDTO> createPost(@RequestBody PostCreateRequest postCreateRequest) {

        PostDTO savedPost = postService.createPost(postCreateRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/posts/" + savedPost.getId());
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(savedPost);
    }

    @Override
    @PatchMapping("/{post_id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable("post_id") Integer id,
                                              @Validated @RequestBody PostUpdateRequest postDTO) {
        if (postDTO.getId() == null) {
            throw new NotFoundException("The Post id does not exists, for post: " + postDTO);
        }

        Optional<PostDTO> updatedPost = postService.updatePostById(id, postDTO);
        if (updatedPost.isEmpty()){
            throw new NotFoundException("The post to updated does not exists");
        }

        return ResponseEntity.status(HttpStatus.OK).body(updatedPost.get());

    }

    @Override
    @DeleteMapping("/{post_id}")
    public ResponseEntity<Boolean> deletePost(@PathVariable("post_id") Integer id) {
        if (!postService.deletePostById(id)){
            throw new NotFoundException("The Post id to delete does not exists: " + id);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
