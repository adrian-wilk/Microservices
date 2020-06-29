package io.microservices.movieinfoservices.resources;

import io.microservices.movieinfoservices.model.Movie;
import io.microservices.movieinfoservices.model.MovieSummary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/movies")
public class MovieResource {

  private RestTemplate restTemplate;

  public MovieResource(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Value("${api.key}")
  private String apiKey;

  @RequestMapping("/{movieId}")
  public Movie getMovieInfo(@PathVariable("movieId") String movieId) {
    MovieSummary movieSummary = restTemplate
        .getForObject("https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" + apiKey,
            MovieSummary.class);
    return new Movie(movieId, movieSummary.getTitle(), movieSummary.getOverview());

  }

}
