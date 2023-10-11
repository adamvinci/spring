package be.vinci.ipl.catflix.authentification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthentificationApplication {

  public static void main(String[] args) {
    SpringApplication.run(AuthentificationApplication.class, args);
  }
}