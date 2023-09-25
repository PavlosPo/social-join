package com.social.join.service;

import com.social.join.dtos.CommentDTO;
import com.social.join.dtos.HashtagDTO;
import com.social.join.dtos.PostDTO;
import com.social.join.dtos.UserDTO;
import com.social.join.entities.User;

import java.util.List;
import java.util.Optional;

public interface IPostService {

    /**
     * Creates a post made by a user
     * @param user  the user that creates the post
     * @param postDTO  the {@link PostDTO} instance to create the Post
     * @return  the new {@link PostDTO} instance with the given id
     */
    PostDTO createPost(User user, PostDTO postDTO);

    /**
     * Gets Posts made by specific User's id {@link  com.social.join.entities.User}
     * @param id    the id of the class {@link  com.social.join.entities.User}
     * @return  a list of {@link UserDTO} instances
     */
    List<PostDTO> getPostsByUserId(int id);

    /**
     * Gets the Post by id
     * @param id    the id of the {@link com.social.join.entities.Post} class
     * @return  {@link Optional} of the {@link PostDTO} class that has the id given,
     * it may result an empty {@link Optional} if the post was not found
     */
    Optional<PostDTO> getPostById(int id);

    /**
     * Deletes a post made by a User, using the Post's id
     * @param id    the {@link com.social.join.entities.Post} id
     * @return  true if the post deleted successfully, false otherwise
     */
    Boolean deletePostById(int id);

    /**
     * Updates the {@link com.social.join.entities.Post} in the database,
     * given a new {@link PostDTO} with the Post's id
     * @param id    the {@link com.social.join.entities.Post} id
     * @param postDTO   a {@link PostDTO} instance to update the Post with
     * @return  the {@link PostDTO} instance that we want to be updated to
     */
    PostDTO updatePostById(int id, PostDTO postDTO);

    /**
     * Gets all the comments  of a {@link com.social.join.entities.Post}
     * searched by Post's id.
     * @param id    the {@link com.social.join.entities.Post}'s id
     * @return  a list of {@link CommentDTO } instances
     */
    List<CommentDTO> getAllTheCommentsByPostId(int id);

    /**
     * Gets all the hashtags a Post has
     * @param id    the id of the {@link com.social.join.entities.Post} instance to search
     * @return  a list of {@link HashtagDTO} instances
     */
    List<HashtagDTO> getAllTheHashtagsByPostId(int id);

    /**
     * Gets the Users that liked the Post
     * @param id    the {@link com.social.join.entities.Post} id to search the likes
     * @return  a list of {@link UserDTO} instances
     */
    List<UserDTO> getUsersThatLikedByPostId(int id);



}
