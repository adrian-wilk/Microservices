package io.microservices.ratingdataservices.resources;

import io.microservices.ratingdataservices.model.Rating;
import io.microservices.ratingdataservices.model.UserRating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ratingsdata")
public class RatingResource {

  @RequestMapping("/movies/{movieId}")
  public Rating getMovieRating(@PathVariable("movieId") String movieId) {
    return new Rating(movieId, 4);
  }

  @RequestMapping("/users/{userId}")
  public UserRating getUserRatings(@PathVariable("userId") String userId) {
    UserRating userRating = new UserRating();
    userRating.initData(userId);
    return userRating;

  }

}
