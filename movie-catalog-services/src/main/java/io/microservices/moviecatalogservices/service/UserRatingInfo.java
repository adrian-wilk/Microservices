package io.microservices.moviecatalogservices.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.microservices.moviecatalogservices.model.Rating;
import io.microservices.moviecatalogservices.model.UserRating;
import java.util.Arrays;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

@Service
public class UserRatingInfo {

  private RestTemplate restTemplate;

  public UserRatingInfo(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @HystrixCommand(fallbackMethod = "getFallBackUserRating")
  public UserRating getUserRating(@PathVariable("userId") String userId) {
    return restTemplate
        .getForObject("http://rating-data-service/ratingsdata/users/" + userId, UserRating.class);
  }

  private UserRating getFallBackUserRating(@PathVariable("userId") String userId) {
    UserRating userRating = new UserRating();
    userRating.setUserId(userId);
    userRating.setRatings(Arrays.asList(
        new Rating("0", 0)
    ));
    return userRating;
  }
}
