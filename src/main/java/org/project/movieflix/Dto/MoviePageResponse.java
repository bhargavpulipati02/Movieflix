package org.project.movieflix.Dto;

import org.project.movieflix.Entity.Movie;

import java.util.List;
// record has inbuilt all args constructor
public record MoviePageResponse(List<MovieDto> movieDto, Integer pageNumber, Integer pageSize,
                                long totalElements, int TotalPages, boolean isLast) {


}
