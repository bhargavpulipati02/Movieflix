package org.project.movieflix.Service;

import org.project.movieflix.Dto.MovieDto;
import org.project.movieflix.Entity.Movie;
import org.project.movieflix.Exceptions.FileEmptyException;
import org.project.movieflix.Exceptions.MovieNotFoundException;
import org.project.movieflix.Repository.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
//        no dulicate files
        if(Files.exists(Paths.get(path+ File.separator+file.getOriginalFilename()))){
            throw new FileEmptyException("File already exists");
        }

        // 1. upload the file


        String uploadedFileName = fileService.UploadFile(path, file);

        // 2. set the value of field 'poster' as filename
        movieDto.setMoviePoster(uploadedFileName);

        // 3. map dto to Movie object
        Movie movie = new Movie(
                null,
                movieDto.getMovieTitle(),
                movieDto.getMovieDirector(),
                movieDto.getStudio(),
                movieDto.getMovieCast(),
                movieDto.getMovieYear(),
                movieDto.getMoviePoster()
        );

        // 4. save the movie object -> saved Movie object
        //Adds or Updates -> save ; save updates if id already present in DB
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

    @Override
    public MovieDto getMovie(Integer movieId) {
//        check data in db and fetch
//        Doubt
        Movie movie= movieRepository.findById(movieId).orElseThrow(()->new MovieNotFoundException("MovieNot found"));

        String posterurl = baseUrl + "/file/" + movie.getMoviePoster();
        MovieDto response = new MovieDto(
                movie.getMovieId(),
                movie.getMovieTitle(),
                movie.getMovieDirector(),
                movie.getStudio(),
                movie.getMovieCast(),
                movie.getMovieYear(),
                movie.getMoviePoster(),
                posterurl
        );
        return response;

    }

    @Override
    public List<MovieDto> getAllMovies() {
        List<Movie> allMovies = movieRepository.findAll();
//        iterate through the list and generate poster url
        List<MovieDto> allMoviesDto = new ArrayList<>();
        for(Movie movie : allMovies) {
            String posterUrl = baseUrl + "/file/" + movie.getMoviePoster();
            MovieDto response = new MovieDto(
                    movie.getMovieId(),
                    movie.getMovieTitle(),
                    movie.getMovieDirector(),
                    movie.getStudio(),
                    movie.getMovieCast(),
                    movie.getMovieYear(),
                    movie.getMoviePoster(),
                    posterUrl
            );
            allMoviesDto.add(response);
        }
        return allMoviesDto;
    }

    @Override
    public MovieDto updateMovie(Integer movieId, MovieDto movieDto, MultipartFile file) throws IOException {
        // 1. check if movie object exists with given movieId
        Movie mv = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException(("Movie not found with id = " + movieId)));

        // 2. if file is null, do nothing
        // if file is not null, then delete existing file associated with the record,
        // and upload the new file
        String fileName = mv.getMoviePoster();
        if (file != null) {
            Files.deleteIfExists(Paths.get(path + File.separator + fileName));
            fileName = fileService.UploadFile(path, file);
        }

        // 3. set movieDto's poster value, according to step2
        movieDto.setMoviePoster(fileName);

        // 4. map it to Movie object
        Movie movie = new Movie(
                mv.getMovieId(),
                movieDto.getMovieTitle(),
                movieDto.getMovieDirector(),
                movieDto.getStudio(),
                movieDto.getMovieCast(),
                movieDto.getMovieYear(),
                movieDto.getMoviePoster()
        );

        // 5. save the movie object -> return saved movie object
        Movie updatedMovie = movieRepository.save(movie);

        // 6. generate posterUrl for it
        String posterUrl = baseUrl + "/file/" + fileName;

        // 7. map to MovieDto and return it
        MovieDto response = new MovieDto(
                movie.getMovieId(),
                movie.getMovieTitle(),
                movie.getMovieDirector(),
                movie.getStudio(),
                movie.getMovieCast(),
                movie.getMovieYear(),
                movie.getMoviePoster(),
                posterUrl
        );

        return response;

    }

    @Override
    public String deleteMovie(Integer movieId) throws IOException {
        // 1. check if movie object exists in DB
        Movie mv = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found with id = " + movieId));
        Integer id = mv.getMovieId();

        // 2. delete the file associated with this object
        Files.deleteIfExists(Paths.get(path + File.separator + mv.getMoviePoster()));

        // 3. delete the movie object
        movieRepository.delete(mv);

        return "Movie deleted with id = " + id;
    }
}
