package org.project.movieflix.Auth.Repository;

import jakarta.transaction.Transactional;
import org.project.movieflix.Auth.Entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String username);

    @Modifying
    @Transactional
    @Query("update User u set u.password=?2 where u.email = ?1")
    void updatePassword(String email, String password);

}