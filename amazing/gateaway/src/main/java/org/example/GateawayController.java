package org.example;

import java.util.Objects;
import org.example.exception.BadRequestException;
import org.example.exception.ConflictException;
import org.example.exception.ForbiddenException;
import org.example.exception.NotFoundException;
import org.example.exception.UnauthorizedException;
import org.example.models.UserWithCredentials;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GateawayController {

  private final GateawayService service;

  public GateawayController(GateawayService service) {
    this.service = service;
  }

  @PostMapping("/authentification/create/{pseudo}")
  public ResponseEntity<Void> createOne(@PathVariable String pseudo,
      @RequestBody UserWithCredentials credentials) {
    if (!Objects.equals(credentials.getPseudo(), pseudo)) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    try {
      service.createOne(credentials);
      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (BadRequestException e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } catch (ConflictException e) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }

  @PostMapping("/authentication/connect")
  public ResponseEntity<String> connect(@RequestBody UserWithCredentials credentials) {

    try {
      String token = service.connect(credentials.toCredentials());
      return new ResponseEntity<>(token, HttpStatus.OK);
    } catch (BadRequestException e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } catch (UnauthorizedException e) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


  }
  @PostMapping("/authentication/verify")
  public ResponseEntity<String> verify(@RequestBody String token) {
    try {
      String pseudo = service.verify(token);
      return new ResponseEntity<>(pseudo, HttpStatus.OK);
    }catch (UnauthorizedException e){
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
 }

  @DeleteMapping("/authentication/{pseudo}")
  public ResponseEntity<Void> deleteCredentials(@PathVariable String pseudo) {
    try {
      service.deleteOne(pseudo);
      return new ResponseEntity<>(HttpStatus.OK);
    }catch (NotFoundException e){
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PutMapping("/authentication/{pseudo}")
  public ResponseEntity<Void> updateOne(@PathVariable String pseudo, @RequestBody UserWithCredentials credentials) {
    if (!Objects.equals(credentials.getPseudo(), pseudo)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    try {
      service.updateOne(credentials);
      return new ResponseEntity<>(HttpStatus.OK);
    }catch (NotFoundException e){
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } catch (BadRequestException e) {
      return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }
}
