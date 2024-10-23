package org.project.movieflix.Auth.Repository;


import org.project.movieflix.Auth.Entitys.ForgotPassword;
import org.project.movieflix.Auth.Entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Integer> {
// 1 and 2 below indicate the parameteres which we are passed
    @Query("select fp from ForgotPassword fp where fp.otp = ?1  and fp.user = ?2")
    Optional<ForgotPassword> findByOtpAndUser(Integer otp, User user);


}
