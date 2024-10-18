package org.project.movieflix.Dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
//DB level validation is not required for the dto class

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {

    private Integer movieId;

    @NotBlank(message = "Please Provide Movie title")
    private String movieTitle;

    @NotBlank(message = "Please Provide Movie director")
    private String movieDirector;

    @NotBlank(message = "Please Provide Movie studio")
    private String studio;

    private Set<String> movieCast;


    private Integer movieYear;


    @NotBlank(message = "Please Provide MoviePoster")
//    actually Image
//    if u store a file in database it becomes an expensive operation
//    we will upload the files in the target folder
    private String moviePoster;

    @NotBlank(message = "Please Provide url for poster")
    private String posterUrl;

}
