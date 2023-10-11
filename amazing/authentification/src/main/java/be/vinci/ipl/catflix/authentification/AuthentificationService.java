package be.vinci.ipl.catflix.authentification;

import be.vinci.ipl.catflix.authentification.models.SafeCredentials;
import be.vinci.ipl.catflix.authentification.models.UnsafeCredentials;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class AuthentificationService {
  private AuthentificationRepository repository;
  private final Algorithm jwtAlgorithm;
  private final JWTVerifier jwtVerifier;

  public AuthentificationService(AuthentificationRepository repository, AuthenticationProperties properties) {
    this.repository = repository;
    this.jwtAlgorithm = Algorithm.HMAC512(properties.getSecret());
    this.jwtVerifier = JWT.require(this.jwtAlgorithm).withIssuer("auth0").build();
  }
  public Iterable<SafeCredentials> getAll(){
    return repository.findAll();
  }
  public boolean createOne(UnsafeCredentials credentials){
    if(repository.existsById(credentials.getPseudo()))
      return false;
    String hashedPassword =  BCrypt.hashpw(credentials.getPassword(), BCrypt.gensalt());
    repository.save(credentials.makeSafe(hashedPassword));
    return true;
  }
  public String connect(UnsafeCredentials credentials){
    SafeCredentials user = repository.findById(credentials.getPseudo()).orElse(null);
    if(user == null) return null;
    if(!BCrypt.checkpw(credentials.getPassword(),user.getHashedPassword())) return null;
    return JWT.create().withIssuer("auth0").withClaim("pseudo",user.getPseudo()).sign(jwtAlgorithm);
  }
  public String verify(String token){
    try {
      String pseudo = jwtVerifier.verify(token).getClaim("pseudo").asString();
      System.out.println(pseudo);
      if (!repository.existsById(pseudo)) return null;
      return pseudo;
    } catch (JWTVerificationException e) {
      return null;
    }
  }
  public boolean deleteOne(String pseudo){
    SafeCredentials user = repository.findById(pseudo).orElse(null);
    if(user==null) return false;
    repository.deleteById(user.getPseudo());
    return true;
  }
  public boolean updateOne(UnsafeCredentials credentials){
    SafeCredentials user = repository.findById(credentials.getPseudo()).orElse(null);
    if(user==null) return false;
    String hashedNewPassword = BCrypt.hashpw(credentials.getPassword(),BCrypt.gensalt());
    repository.save(credentials.makeSafe(hashedNewPassword));
    return true;
  }
}
