package org.project.movieflix.Auth.Entitys;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer TokenId;

    @Column(nullable = false, length= 500)
    @NotBlank(message = "please enter refresh token")
    private String refreshToken;

    @Column(nullable=false)
    private Instant expirationTime;

    @OneToOne
    private User user;
}
