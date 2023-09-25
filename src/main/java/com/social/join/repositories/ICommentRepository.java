package com.social.join.repositories;


import com.social.join.entities.Comment;
import com.social.join.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ICommentRepository extends JpaRepository<Comment, Integer> {
}
