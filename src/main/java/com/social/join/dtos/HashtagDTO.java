package com.social.join.dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HashtagDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private Integer id;

    @Version
    private Integer version;

    @Column(name = "HASHTAG")
    private String hashtag;

    @Column(name = "DESCRIPTION")
    private String description;
}
