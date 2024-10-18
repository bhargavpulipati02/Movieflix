package org.project.movieflix.Service;

import org.project.movieflix.Dto.MovieDto;
import org.project.movieflix.Entity.Movie;
import org.project.movieflix.Repository.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.file.Files;
@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;

    private final FileService fileService;

    @Value("${project.poster}")
    private String path;

    @Value("${base.url}")
    private String baseUrl;


    public MovieServiceImpl(MovieRepository movieRepository, FileService fileService) {
        this.movieRepository = movieRepository;
        this.fileService = fileService;
    }

    @Override
    public MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException {
        // 1. upload the file

        String uploadedFileName = fileService.UploadFile(path, file);

        // 2. set the value of field 'poster' as filename
        movieDto.setMoviePoster(uploadedFileName);

        // 3. map dto to Movie object
        Movie movie = new Movie(
                movieDto.getMovieId(),
                movieDto.getMovieTitle(),
                movieDto.getMovieDirector(),
                movieDto.getStudio(),
                movieDto.getMovieCast(),
                movieDto.getMovieYear(),
                movieDto.getMoviePoster()
        );

        // 4. save the movie object -> saved Movie object
        Movie savedMovie = movieRepository.save(movie);

        // 5. generate the posterUrl
        String posterUrl = baseUrl + "/file/" + uploadedFileName;

        // 6. map Movie object to DTO object and return it
        MovieDto response = new MovieDto(
                savedMovie.getMovieId(),
                savedMovie.getMovieTitle(),
                savedMovie.getMovieDirector(),
                savedMovie.getStudio(),
                savedMovie.getMovieCast(),
                savedMovie.getMovieYear(),
                savedMovie.getMoviePoster(),
                posterUrl
        );

        return response;
    }
}
