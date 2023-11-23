package com.social.join.repositories;


import com.social.join.entities.Post;
import com.social.join.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IPostRepository extends JpaRepository<Post, Integer> {

    List<Post> getPostsByUserCreated_Id(int id);
}