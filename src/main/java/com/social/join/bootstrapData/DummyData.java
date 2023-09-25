package com.social.join.bootstrapData;

import com.social.join.entities.Comment;
import com.social.join.entities.Hashtag;
import com.social.join.entities.Post;
import com.social.join.entities.User;
import com.social.join.repositories.ICommentRepository;
import com.social.join.repositories.IHashtagRepository;
import com.social.join.repositories.IPostRepository;
import com.social.join.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DummyData implements CommandLineRunner {

    private final IUserRepository userRepository;
    private final IPostRepository postRepository;
    private final IHashtagRepository hashtagRepository;
    private final ICommentRepository commentRepository;


    @Override
    public void run(String... args) throws Exception {
        List<User> usersToSave = DummyDataGenerator.generateUsers(10);
        List<Post> postsToSave = DummyDataGenerator.generatePosts(usersToSave, 10);
        List<Comment> commentsToSave = DummyDataGenerator.generateComments(usersToSave, postsToSave, 10);
        List<Hashtag> hashtagsToSave = DummyDataGenerator.generateHashtags(10);

        userRepository.saveAll(usersToSave);
        postRepository.saveAll(postsToSave);
        commentRepository.saveAll(commentsToSave);
        hashtagRepository.saveAll(hashtagsToSave);
        usersToSave.forEach(System.out::println);
    }


}
