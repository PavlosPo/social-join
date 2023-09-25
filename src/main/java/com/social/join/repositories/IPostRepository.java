package com.social.join.repositories;


import com.social.join.entities.Post;
import com.social.join.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IPostRepository extends JpaRepository<Post, Integer> {
}
