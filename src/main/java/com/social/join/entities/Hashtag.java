package com.social.join.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Set;
import java.util.UUID;


@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hashtag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Integer id;

    @Version
    private Integer version;

    @Column(name = "HASHTAG")
    private String hashtag;

    @Column(name = "DESCRIPTION")
    private String description;
}
