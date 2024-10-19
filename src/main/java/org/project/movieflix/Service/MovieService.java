package org.project.movieflix.Service;

import org.project.movieflix.Dto.MovieDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MovieService {
    MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException;
    MovieDto getMovie(Integer movieId);
    List<MovieDto> getAllMovies();
    MovieDto updateMovie(Integer Id,MovieDto movieDto, MultipartFile file) throws IOException;
    String deleteMovie(Integer movieId) throws IOException;



    





}
