package org.project.movieflix.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.project.movieflix.Dto.MovieDto;
import org.project.movieflix.Dto.MoviePageResponse;
import org.project.movieflix.Exceptions.FileEmptyException;
import org.project.movieflix.Service.MovieService;
import org.project.movieflix.utils.AppConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/movie")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/add-movie")
    public ResponseEntity<MovieDto> addMovieHandler(@RequestPart MultipartFile file,
                                                    @RequestPart String movieDto) throws IOException {
        if(file.isEmpty()){
            throw new FileEmptyException("File is Empty");
        }

        MovieDto dto = convertToMovieDto(movieDto);
        return new ResponseEntity<>(movieService.addMovie(dto, file), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<MovieDto> getMovieByIdHandler(@PathVariable Integer id) {
        return new ResponseEntity<>(movieService.getMovie(id), HttpStatus.OK);

    }
    @GetMapping("allmovies")
    public ResponseEntity<List<MovieDto>> getMovies(){
        return new ResponseEntity<>(movieService.getAllMovies(), HttpStatus.OK);

    }
//requestparam is ?
    @GetMapping("allmoviespagination")
    public ResponseEntity<MoviePageResponse> getMoviesWithPagination(
            @RequestParam(defaultValue = AppConstant.PAGE_NUMBER,required = false) Integer pageNo,
            @RequestParam(defaultValue = AppConstant.PAGE_SIZE,required = false) Integer pageSize

    ) {
        return ResponseEntity.ok(movieService.getAllMoviesWithPagination(pageNo,pageSize));

    }


    @GetMapping("allmoviespagesort")
    public ResponseEntity<MoviePageResponse> getMoviesWithPaginationAndSorting(
            @RequestParam(defaultValue = AppConstant.PAGE_NUMBER,required = false) Integer pageNo,
            @RequestParam(defaultValue = AppConstant.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(defaultValue = AppConstant.SORT_BY,required = false) String sortBy,
            @RequestParam(defaultValue = AppConstant.SORT_ORDER,required = false) String dir
    ) {
        return ResponseEntity.ok(movieService.getAllMoviesWithPaginationAndSorting(pageNo,pageSize,sortBy,dir));

    }

    @PutMapping("{id}/update")
    public ResponseEntity<MovieDto> updateMovieHandler(@PathVariable Integer id, @RequestPart String movieDto,@RequestPart MultipartFile file) throws IOException {
        MovieDto dto = convertToMovieDto(movieDto);
        return new ResponseEntity<>(movieService.updateMovie(id,dto,file), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}/del")
    public ResponseEntity<String> deleteMovieHandler(@PathVariable Integer id) throws IOException {
        return new  ResponseEntity<>(movieService.deleteMovie(id),HttpStatus.OK
        );
    }

    private MovieDto convertToMovieDto(String movieDtoObj) throws JsonProcessingException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(movieDtoObj, MovieDto.class);
    }
    }
