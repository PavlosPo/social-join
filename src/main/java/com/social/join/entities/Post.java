package com.social.join.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Fetch;
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
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private Integer id = null;

    @NotNull
    @NotBlank
    @Column(name = "CONTENT", length = 512, nullable = false, updatable = true, unique = false)
    private String content;

    @ManyToMany
    @JoinTable(name = "POSTS_USERS_LIKES", joinColumns = @JoinColumn(name = "POST_ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"))    // create second "in-between table"
    private Set<User> usersLikedIt;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User userCreated;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @ManyToMany
    @JoinTable(name = "POSTS_HASHTAGS", joinColumns = @JoinColumn(name = "POST_ID"),
            inverseJoinColumns = @JoinColumn(name = "HASHTAG_ID", referencedColumnName = "ID"))
    private List<Hashtag> hashtags;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
