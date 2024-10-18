package org.project.movieflix.Service;

import org.project.movieflix.Dto.MovieDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MovieService {
    MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException;
//    MovieDto getMovie(Integer movieId);




    





}
