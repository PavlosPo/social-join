package com.social.join.repositories;


import com.social.join.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Integer> {

    Page<User> findAllByFirstnameLikeIgnoreCase(String firstname, Pageable pageable);
    Page<User> findAllByUsernameLikeIgnoreCase(String username, Pageable pageable);
    Page<User> findAllByLastnameLikeIgnoreCase(String lastname, Pageable pageable);
    // List<User> findAllByFirstnameLikeIgnoreCaseAndLastnameLikeIgnoreCase(String firstname, String lastname);

    Page<User> findAllByUsernameLikeIgnoreCaseAndAndFirstnameLikeIgnoreCase(String username, String firstname, Pageable pageable);
    Page<User> findAllByUsernameLikeIgnoreCaseAndLastnameLikeIgnoreCase(String username, String lastname, Pageable pageable);

}
