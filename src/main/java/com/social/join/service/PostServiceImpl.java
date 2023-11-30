package com.social.join.service;

import com.social.join.dtos.PostCreateRequest;
import com.social.join.dtos.PostDTO;
import com.social.join.dtos.PostUpdateRequest;
import com.social.join.dtos.UserDTO;
import com.social.join.entities.Post;
import com.social.join.mappers.IPostMapper;
import com.social.join.mappers.IUserMapper;
import com.social.join.repositories.IPostRepository;
import com.social.join.service.exceptions.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class PostServiceImpl implements IPostService {
    private final IPostRepository postRepository;
    private final IPostMapper postMapper;
    private final IUserMapper userMapper;

    @Override
    public PostDTO createPost(PostCreateRequest postCreateRequest) {
        System.out.println("I am At service: "+ postCreateRequest);
        return postMapper.postToPostDTO(postRepository.save(postMapper.postCreateRequestToPost(postCreateRequest)));
    }

    @Override
    public List<PostDTO> getPostsByUserId(int id) {
        return postRepository.getPostsByUserCreated_Id(id).stream()
                .map(postMapper::postToPostDTO).toList();
    }

    @Override
    public Optional<PostDTO> getPostById(int id) {
        return Optional.ofNullable(postMapper.postToPostDTO(postRepository.findById(id).orElse(null)));
    }

    @Override
    public Boolean deletePostById(int id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<PostDTO> updatePostById(int id, PostUpdateRequest postUpdateRequest) {
        AtomicReference<Optional<PostDTO>> atomicReference = new AtomicReference<>();

        postRepository.findById(id).ifPresentOrElse(foundPost -> {
            Post mappedPost = postMapper.postUpdateRequestToPost(postUpdateRequest);
            foundPost.setContent(mappedPost.getContent());
            foundPost.setUsersWhoLikedThisPost(mappedPost.getUsersWhoLikedThisPost());
//            foundPost.setComments(mappedPost.getComments());
            foundPost.setUpdateDate(LocalDateTime.now());
            atomicReference.set(Optional.of(postMapper.postToPostDTO(postRepository.save(foundPost))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });
        return atomicReference.get();
    }

    @Override
    public List<UserDTO> getUsersThatLikedByPostId(int id) throws PostNotFoundException {
        AtomicReference<List<UserDTO>> atomicReference = new AtomicReference<>();
        Optional<Post> foundPost = postRepository.findById(id);

        // Check if there is a post
        if (foundPost.isEmpty()) {
            throw new PostNotFoundException();
        }

        foundPost.ifPresent(post -> {
            atomicReference.set(post.getUsersWhoLikedThisPost()
                    .stream()
                    .map(userMapper::userToUserDTO)
                    .collect(Collectors.toList()));
        });
        return atomicReference.get();
    }
}
