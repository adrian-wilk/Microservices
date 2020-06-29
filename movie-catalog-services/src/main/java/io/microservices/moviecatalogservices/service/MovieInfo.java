package io.microservices.moviecatalogservices.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.microservices.moviecatalogservices.model.CatalogItem;
import io.microservices.moviecatalogservices.model.Movie;
import io.microservices.moviecatalogservices.model.Rating;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieInfo {

  private RestTemplate restTemplate;

  public MovieInfo(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @HystrixCommand(fallbackMethod = "getFallBackCatalogItem")
  public CatalogItem getCatalogItem(Rating rating) {
    Movie movie = restTemplate
        .getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
    return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
  }

  private CatalogItem getFallBackCatalogItem(Rating rating) {
    return new CatalogItem("Movie not found", "", rating.getRating());
  }
}
