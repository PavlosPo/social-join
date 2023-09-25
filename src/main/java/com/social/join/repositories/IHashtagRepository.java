package com.social.join.repositories;

import com.social.join.entities.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IHashtagRepository extends JpaRepository<Hashtag, Integer> {
}
