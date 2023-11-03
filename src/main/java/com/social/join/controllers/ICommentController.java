package com.social.join.controllers;

import com.social.join.dtos.CommentDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public interface ICommentController {

    @GetMapping("posts/{postId}/comments")
    ResponseEntity<List<CommentDTO>> getAllComments(@PathVariable("postId") Integer postId);

    @GetMapping("posts/{postId}/comments/{commentId}")
    ResponseEntity<CommentDTO> getCommentById(@PathVariable(value = "postId", required = true) Integer postId,
                                        @PathVariable(value = "commentId", required = true) Integer commentId);

    @PostMapping("/posts/{postId}/comments/")
    ResponseEntity<CommentDTO> createComment(@PathVariable(value = "postId", required = true) Integer postId, CommentDTO commentDTO);

    @PutMapping("posts/{postId}/comments/{commentId}")
    ResponseEntity<CommentDTO> updateComment(@PathVariable(value = "postId", required = true) Integer postId,
                                       @PathVariable(value = "commentId", required = true) Integer commentId,
                                       @RequestBody CommentDTO commentDTO);

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    ResponseEntity<Boolean> deleteComment(@PathVariable(value = "postId", required = true) Integer postId,
                                       @PathVariable(value = "commentId", required = true) Integer commentId);


}
