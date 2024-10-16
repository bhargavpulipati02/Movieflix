package org.project.movieflix.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movieId;

    @Column(nullable=false,length=255)
    @NotBlank(message = "Please Provide Movie title")
    private String movieTitle;

    @Column(nullable=false,length=255)
    @NotBlank(message = "Please Provide Movie director")
    private String movieDirector;

    @Column(nullable=false,length=255)
    @NotBlank(message = "Please Provide Movie studio")
    private String studio;

    @ElementCollection
    @CollectionTable(name="movie_cast")
    private Set<String> movieCast;


    @NotBlank(message = "Please Provide MovieYear")
    private Integer movieYear;

    @Column(nullable=false,length=255)
    @NotBlank(message = "Please Provide MoviePoster")
//    actually Image
//    if u store a file in database it becomes an expensive operation
//    we will upload the files in the target folder
    private String moviePoster;

}
