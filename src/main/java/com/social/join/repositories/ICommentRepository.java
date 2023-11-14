package com.social.join.repositories;


import com.social.join.dtos.CommentDTO;
import com.social.join.entities.Comment;
import com.social.join.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ICommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> getCommentsByPostId(Integer postId);
}
