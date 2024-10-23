package org.project.movieflix.Auth.Entitys;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @NotBlank(message = "name cannot be empty")
    private String name;

    @NotBlank(message = "username cannot be empty")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "email cannot be empty")
    @Column(unique = true)
    @Email(message = "email format please")
    private String email;

    @NotBlank(message = "password cannot be empty")
    @Size(min= 5  ,message = "atleast 5 chars please")
    private String password;

//    owner Table is user ->if u change User user in User table u need to change here- Nonowning
    @OneToOne(mappedBy="user")
    private RefreshToken refreshToken;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "user") // Change here
    private List<ForgotPassword> forgotPasswords = new ArrayList<>();

//    @OneToOne(mappedBy = "user")
//    private ForgotPassword forgotPassword;

    private boolean isEnabled = true;
    private boolean isAccountNonExpired=true;
    private boolean isAccountNonLocked=true;
    private boolean isCredentialsNonExpired=true;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
