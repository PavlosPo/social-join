package com.social.join.repositories;


import com.social.join.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IUserRepository extends JpaRepository<User, Integer> {

    List<User> findAllByFirstnameLikeIgnoreCase(String firstname);
    List<User> findAllByUsernameLikeIgnoreCase(String username);
    List<User> findAllByLastnameLikeIgnoreCase(String lastname);
    // List<User> findAllByFirstnameLikeIgnoreCaseAndLastnameLikeIgnoreCase(String firstname, String lastname);
}
