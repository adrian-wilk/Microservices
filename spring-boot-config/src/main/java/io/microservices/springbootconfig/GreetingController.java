package io.microservices.springbootconfig;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class GreetingController {

  private DbSettings dbSettings;
  private Environment environment;

  public GreetingController(DbSettings dbSettings, Environment environment) {
    this.dbSettings = dbSettings;
    this.environment = environment;
  }

  @Value("${my.greeting: default value}")
  private String greetingMessage;

  @Value("Some static message")
  private String staticMessage;

  @Value("${my.list.values}")
  private List<String> listValues;

  @Value("#{${db.values}}")
  private Map<String, String> dbValues;


  @GetMapping("/greeting")
  public String greeting() {
    return greetingMessage + staticMessage + listValues + dbValues + dbSettings.getConnection() + dbSettings.getHost() + dbSettings.getPort();
  }

  @GetMapping("/envdetails")
  public String envDetails() {
return environment.toString();
  }

}
