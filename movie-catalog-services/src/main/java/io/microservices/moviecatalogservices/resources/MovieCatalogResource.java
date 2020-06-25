package io.microservices.moviecatalogservices.resources;

import io.microservices.moviecatalogservices.model.CatalogItem;
import java.util.Collections;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

  @RequestMapping("/{userId}")
  public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
    return Collections.singletonList(
        new CatalogItem("Titanic", "Cool movie", 4)
    );
  }
}
