package io.microservices.moviecatalogservices.resources;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.microservices.moviecatalogservices.model.CatalogItem;
import io.microservices.moviecatalogservices.model.Movie;
import io.microservices.moviecatalogservices.model.Rating;
import io.microservices.moviecatalogservices.model.UserRating;
import io.microservices.moviecatalogservices.service.MovieInfo;
import io.microservices.moviecatalogservices.service.UserRatingInfo;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.core.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

  private RestTemplate restTemplate;
  private WebClient.Builder webClientBuilder;
  private MovieInfo movieInfo;
  private UserRatingInfo userRatingInfo;

  public MovieCatalogResource(RestTemplate restTemplate, WebClient.Builder webClientBuilder,
      MovieInfo movieInfo, UserRatingInfo userRatingInfo) {
    this.restTemplate = restTemplate;
    this.webClientBuilder = webClientBuilder;
    this.movieInfo = movieInfo;
    this.userRatingInfo = userRatingInfo;
  }

  @RequestMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON)
  public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

    UserRating ratings = userRatingInfo.getUserRating(userId);

    return ratings.getRatings()
        .stream()
        .map(rating -> movieInfo.getCatalogItem(rating))
        .collect(Collectors.toList());
  }

}

// WebClient method
//      Movie movie = webClientBuilder.build()
//          .get()
//          .uri("http://localhost:8081/movies/" + rating.getMovieId())
//          .retrieve()
//          .bodyToMono(Movie.class)
//          .block();
