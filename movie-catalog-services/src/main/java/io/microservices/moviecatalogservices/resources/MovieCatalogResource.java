package io.microservices.moviecatalogservices.resources;

import io.microservices.moviecatalogservices.model.CatalogItem;
import io.microservices.moviecatalogservices.model.Movie;
import io.microservices.moviecatalogservices.model.Rating;
import io.microservices.moviecatalogservices.model.UserRating;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
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

  public MovieCatalogResource(RestTemplate restTemplate,  WebClient.Builder webClientBuilder ) {
    this.restTemplate = restTemplate;
    this.webClientBuilder = webClientBuilder;
  }

  @RequestMapping("/{userId}")
  public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {


    UserRating ratings = restTemplate
        .getForObject("http://localhost:8083/ratingsdata/users/" + userId, UserRating.class);

    return ratings.getRatings()
        .stream()
        .map(rating -> {

      // RestTemplate method
      Movie movie = restTemplate
          .getForObject("http://localhost:8081/movies/" + rating.getMovieId(), Movie.class);

      // WebClient method
//      Movie movie = webClientBuilder.build()
//          .get()
//          .uri("http://localhost:8081/movies/" + rating.getMovieId())
//          .retrieve()
//          .bodyToMono(Movie.class)
//          .block();

      return new CatalogItem(movie.getName(), "Cool movie", rating.getRating());
    })
        .collect(Collectors.toList());

  }
}
