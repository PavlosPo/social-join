package com.social.join.bootstrapData;

import com.social.join.entities.Post;
import com.social.join.entities.User;
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
//    private final IHashtagRepository hashtagRepository;
//    private final ICommentRepository commentRepository;

    private List<User> usersToSave;
    private List<Post> postsToSave;
//    private List<Comment> commentsToSave;
//    List<Hashtag> hashtagsToSave;

    @Override
    public void run(String... args) throws Exception {
        usersToSave = DummyDataGenerator.generateUsers(10);
        postsToSave = DummyDataGenerator.generatePosts(usersToSave, 10);
//        commentsToSave = DummyDataGenerator.generateComments(usersToSave, postsToSave, 10);
//        hashtagsToSave = DummyDataGenerator.generateHashtags(10);

        userRepository.saveAll(usersToSave);
        postRepository.saveAll(postsToSave);
//        commentRepository.saveAll(commentsToSave);
//        hashtagRepository.saveAll(hashtagsToSave);
        usersToSave.forEach(System.out::println);
        postsToSave.forEach(System.out::println);
//        commentsToSave.forEach(System.out::println);
//        hashtagsToSave.forEach(System.out::println);
    }


    public Object getAllUsers(Object any, Object any1, Object any2, Object any3, Object any4) {
        return usersToSave;
    }
}
