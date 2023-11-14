package com.social.join.service;

import com.social.join.dtos.CommentDTO;
import com.social.join.dtos.PostDTO;
import com.social.join.dtos.UserDTO;
import com.social.join.entities.Comment;
import com.social.join.entities.Post;
import com.social.join.entities.User;
import com.social.join.mappers.ICommentMapper;
import com.social.join.mappers.IPostMapper;
import com.social.join.mappers.IUserMapper;
import com.social.join.repositories.ICommentRepository;
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
public class CommentServiceImpl implements ICommentService {
    private final ICommentRepository commentRepository;
    private final ICommentMapper commentMapper;
    private final IUserMapper userMapper;
    private final IPostMapper postMapper;

    @Override
    public CommentDTO createComment(UserDTO userDTO, PostDTO postDTO, CommentDTO commentDTO) {
        User user = userMapper.userDTOToUser(userDTO);
        Post post = postMapper.postDTOToPost(postDTO);
        Comment comment = commentMapper.commentDTOToComment(commentDTO);    // here we remove some fields
        // because we have the cycle prevention code, that's why we get a null pointer exception error
        // fix the commentDTOToComment mapper.

        comment.setUserCreatedIt(user);
        comment.setPost(post);

        Comment savedComment = commentRepository.save(comment);

        return commentMapper.commentToCommentDTO(savedComment);

    }

    @Override
    public Optional<CommentDTO> getCommentById(int id) {
        return Optional.ofNullable(commentMapper.commentToCommentDTO(commentRepository.findById(id).orElse(null)));
    }

    @Override
    public List<CommentDTO> getCommentsByPostId(int postId) {
        return commentRepository.getCommentsByPostId(postId).stream()
                .map(commentMapper::commentToCommentDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CommentDTO> updateCommentById(int id, CommentDTO commentDTO) {
        AtomicReference<Optional<CommentDTO>> atomicReference = new AtomicReference<>();

        commentRepository.findById(id).ifPresentOrElse(foundComment -> {
            Comment mappedComment = commentMapper.commentDTOToComment(commentDTO);
            foundComment.setContent(mappedComment.getContent());
            foundComment.setUsersWhoLikedThisComment(mappedComment.getUsersWhoLikedThisComment());
            foundComment.setUpdatedDate(LocalDateTime.now());
            atomicReference.set(Optional.of(commentMapper.commentToCommentDTO(commentRepository.save(foundComment))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });
        return  atomicReference.get();
    }

    @Override
    public Boolean deleteCommentById(int id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
            return true;
        }
        return false;
    }


}
