package com.social.join.bootstrapData;

import com.social.join.entities.Post;
import com.social.join.entities.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DummyDataGenerator {
    public DummyDataGenerator() {}

    public static List<User> generateUsers(int numUsers) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < numUsers; i++) {
            User user = User.builder()
                    .firstname("User" + i)
                    .lastname("Lastname" + i)
                    .username("username" + i)
                    .email("user" + i + "@example.com")
                    .password("password" + i)
                    .build();
            users.add(user);
        }
        return users;
    }

    public static List<Post> generatePosts(List<User> users, int numPosts) {
        List<Post> posts = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < numPosts; i++) {
            User user = users.get(random.nextInt(users.size()));
            Post post = Post.builder()
                    .content("Post content " + i)
                    .userCreated(user)
                    .createdDate(LocalDateTime.now())
                    .build();
            posts.add(post);
        }
        return posts;
    }


//    public static List<Hashtag> generateHashtags(int numHashtags) {
//        List<Hashtag> hashtags = new ArrayList<>();
//        for (int i = 0; i < numHashtags; i++) {
//            Hashtag hashtag = Hashtag.builder()
//                    .hashtag("hashtag" + i)
//                    .description("Description for hashtag " + i)
//                    .build();
//            hashtags.add(hashtag);
//        }
//        return hashtags;
//    }

//    public static List<Comment> generateComments(List<User> users, List<Post> posts, int numComments) {
//        List<Comment> comments = new ArrayList<>();
//        Random random = new Random();
//        for (int i = 0; i < numComments; i++) {
//            User user = users.get(random.nextInt(users.size()));
//            Post post = posts.get(random.nextInt(posts.size()));
//            Comment comment = Comment.builder()
//                    .userCreatedIt(user)
//                    .content("Comment content " + i)
//                    .post(post)
//                    .createdDate(LocalDateTime.now())
//                    .build();
//            comments.add(comment);
//        }
//        return comments;
//    }
}
