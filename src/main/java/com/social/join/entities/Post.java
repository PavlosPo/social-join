package com.social.join.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.repository.cdi.Eager;

import java.time.LocalDateTime;
import java.util.*;


@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Integer id;

    @Column(name = "CONTENT", length = 512, nullable = false, updatable = true, unique = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User userCreated;

//    @Builder.Default    // Lombok will Initialize empty HashSet()
//    @OneToMany(mappedBy = "post")
//    private List<Comment> comments = new ArrayList<>();

    @Builder.Default
    @ManyToMany(mappedBy = "likedPosts")
    @Lazy(value = false)
    private List<User> usersWhoLikedThisPost = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "UPDATED_DATE")
    private LocalDateTime updateDate;

//    public boolean setComment(Comment comment) {
//        if (comment == null) {
//            return false;  // Comment is null, not added
//        }
//
//        if (comments.contains(comment)) {
//            return false;  // Comment already exists, not added
//        }
//
//        this.comments.add(comment);
//        comment.setPost(this);
//        return true;  // Comment added
//    }

    public void addUserWhoLikedThisPost(User user) {
        // If Post instance don't have yet the user
        if (!this.getUsersWhoLikedThisPost().contains(user)) {
            this.getUsersWhoLikedThisPost().add(user);
        }

        // If User instance don't have yet this post
        if (!user.getLikedPosts().contains(this)) {
            user.getLikedPosts().add(this);
        }
    }
}
