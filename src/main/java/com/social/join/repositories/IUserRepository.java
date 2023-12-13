package com.social.join.repositories;


import com.social.join.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {

    Page<User> findAllByFirstnameLikeIgnoreCase(String firstname, Pageable pageable);
    Page<User> findAllByLastnameLikeIgnoreCase(String lastname, Pageable pageable);

    Page<User> findAllByUsernameLikeIgnoreCaseAndAndFirstnameLikeIgnoreCase(String username, String firstname, Pageable pageable);

    Optional<User> findByUsername(String username);
}
