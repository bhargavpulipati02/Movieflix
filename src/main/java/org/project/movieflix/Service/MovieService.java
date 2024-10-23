package org.project.movieflix.Service;

import org.project.movieflix.Dto.MovieDto;
import org.project.movieflix.Dto.MoviePageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MovieService {
    MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException;
    MovieDto getMovie(Integer movieId);
//    This is the method which requires Pagination and sorting
    List<MovieDto> getAllMovies();
    MovieDto updateMovie(Integer Id,MovieDto movieDto, MultipartFile file) throws IOException;
    String deleteMovie(Integer movieId) throws IOException;


    MoviePageResponse getAllMoviesWithPagination(Integer pageNumber,Integer PageSize);
// dir-> asc,desc , sortby-> by what id,..
    MoviePageResponse getAllMoviesWithPaginationAndSorting(Integer pageNumber,Integer pageSize,
                                                           String sortBy,String dir);

    





}
