package org.project.movieflix.Controller;


import org.project.movieflix.Auth.Entitys.ForgotPassword;
import org.project.movieflix.Auth.Entitys.User;
import org.project.movieflix.Auth.Repository.ForgotPasswordRepository;
import org.project.movieflix.Auth.Repository.UserRepository;
import org.project.movieflix.Auth.Utils.ChangePassword;
import org.project.movieflix.Dto.MailBody;
import org.project.movieflix.Service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/forgotPassword")
public class ForgotPasswordController {

    private final UserRepository userRepository;

    public ForgotPasswordController(UserRepository userRepository, EmailService emailService, ForgotPasswordRepository forgotPasswordRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.forgotPasswordRepository = forgotPasswordRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private final EmailService emailService;

    private final ForgotPasswordRepository forgotPasswordRepository;

    private final PasswordEncoder passwordEncoder;


    //    send mail for email verification
     @PostMapping("/verifyMail/{email}")
    public ResponseEntity<String> verifyMail(@PathVariable String email) {
         User user = userRepository.findByEmail(email)
                 .orElseThrow(()-> new UsernameNotFoundException("please provide valid email"));
         int otp=otpGenerator();
         MailBody mailBody = MailBody.builder()
                 .to(email)
                 .text("This is the OTP for forgot password:"+ otp)
                 .subject("Otp for forgot password")
                 .build();

         ForgotPassword fp =ForgotPassword.builder()
                 .otp(otp)
                 .expirationTime(new Date(System.currentTimeMillis() + 700*1000))
                 .user(user)
                 .build();
         emailService.sendSimpleMessage(mailBody);
         forgotPasswordRepository.save(fp);
         return ResponseEntity.ok("Email sent for verification");
     }

    @PostMapping("/verifyotp/{otp}/{email}")
     public ResponseEntity<String> verifyOtp(@PathVariable Integer otp, @PathVariable String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("please provide valid email"));
        ForgotPassword fp =
                forgotPasswordRepository.findByOtpAndUser(otp, user).orElseThrow(()-> new UsernameNotFoundException("please provide valid otp"));
        if(fp.getExpirationTime() .before(Date.from(Instant.now()))){
            forgotPasswordRepository.delete(fp);
            return new ResponseEntity<>("Otp expired", HttpStatus.EXPECTATION_FAILED);

        }
        return ResponseEntity.ok("Otp verified");


    }


    @PostMapping("/changepassword/{email}")
    public ResponseEntity<String> changePasswordHandler(@RequestBody ChangePassword changePassword, @PathVariable String email) {
         if(!Objects.equals(changePassword.password(),changePassword.repeatPassword())){
             return new ResponseEntity<>("Please Enter same pwds",HttpStatus.BAD_REQUEST);
         }
         String encodedPassword = passwordEncoder.encode((changePassword.password()));
         userRepository.updatePassword(email, encodedPassword);
         return ResponseEntity.ok("Password changed successfully");


    }


     private Integer otpGenerator() {
        Random random = new Random();
//        6 digit random no
        return random.nextInt(100000,999999);
     }
}
