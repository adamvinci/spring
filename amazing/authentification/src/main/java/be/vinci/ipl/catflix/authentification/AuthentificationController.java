package be.vinci.ipl.catflix.authentification;

import be.vinci.ipl.catflix.authentification.models.SafeCredentials;
import be.vinci.ipl.catflix.authentification.models.UnsafeCredentials;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthentificationController {
  private final AuthentificationService service;

  public AuthentificationController(AuthentificationService service) {
    this.service = service;
  }

  @GetMapping("/authentification/all")
  public Iterable<SafeCredentials> getAll(){
    return service.getAll();
  }

  @PostMapping("/authentification/create/{pseudo}")
  public ResponseEntity<Void> createOne(@PathVariable String pseudo,@RequestBody UnsafeCredentials unsafeCredentials){
    if(!pseudo.equals(unsafeCredentials.getPseudo()) || unsafeCredentials.invalid()) return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    boolean created = service.createOne(unsafeCredentials);
    if(!created) return new ResponseEntity<>(HttpStatus.CONFLICT);
    return new ResponseEntity<>(HttpStatus.CREATED);

  }

  @PostMapping("/authentification/connect")
  public ResponseEntity<String> connect(@RequestBody UnsafeCredentials unsafeCredentials){
    if(unsafeCredentials.invalid()) return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    String token= service.connect(unsafeCredentials);
    if(token == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    return new ResponseEntity<>(token,HttpStatus.OK);

  }
  @PostMapping("/authentification/verify")
  public ResponseEntity<String> verify(@RequestBody String token){
    String pseudo = service.verify(token);
    System.out.println(pseudo);


    if (pseudo == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    return new ResponseEntity<>(pseudo, HttpStatus.OK);
  }
  @DeleteMapping("/authentification/delete/{pseudo}")
  public ResponseEntity<Void> deleteOne(@PathVariable String pseudo){
    boolean deleted = service.deleteOne(pseudo);
    if(!deleted) return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    return new ResponseEntity<>(HttpStatus.OK);
  }
  @PutMapping("/authentification/update/{pseudo}")
  public ResponseEntity<Void> updateOne(@PathVariable String pseudo,@RequestBody UnsafeCredentials unsafeCredentials){
    if (!Objects.equals(unsafeCredentials.getPseudo(), pseudo)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    if (unsafeCredentials.invalid()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    boolean updated = service.updateOne(unsafeCredentials);
    if(!updated) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
