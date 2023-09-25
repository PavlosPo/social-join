package com.social.join.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;


@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = false)
    private User userCreatedIt;

    @Column(name = "CONTENT", length = 512, nullable = false, updatable = true, unique = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "COMMENT_ID", referencedColumnName = "ID", nullable = false)
    private Post post;

    @ManyToMany
    @JoinTable(name = "COMMENTS_HASHTAGS", joinColumns = @JoinColumn(name = "COMMENT_ID"),
            inverseJoinColumns = @JoinColumn(name = "HASHTAG_ID", referencedColumnName = "ID"))
    private Set<Hashtag> hashtag;


    @ManyToMany
    @JoinTable(name = "COMMENTS_USERS_LIKES", joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "POST_ID", referencedColumnName = "ID"))
    private List<User> usersLikedIt;

    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
