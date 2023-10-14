package org.example.data;

import org.example.models.Credentials;
import org.example.models.UserWithCredentials;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
@FeignClient(name = "authentication")
public interface AuthentificationProxy {
  @PostMapping("/authentification/create/{pseudo}")
   void createOne(@PathVariable String pseudo,@RequestBody UserWithCredentials unsafeCredentials);

  @PostMapping("/authentification/connect")
  String connect(@RequestBody Credentials unsafeCredentials);

  @PostMapping("/authentification/verify")
  String verify(@RequestBody String token);

  @DeleteMapping("/authentification/delete/{pseudo}")
  void deleteOne(@PathVariable String pseudo);

  @PutMapping("/authentification/update/{pseudo}")
  void updateOne(@PathVariable String pseudo,@RequestBody Credentials unsafeCredentials);
}
