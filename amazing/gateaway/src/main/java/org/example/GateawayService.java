package org.example;

import feign.FeignException;
import org.example.data.AuthentificationProxy;
import org.example.exception.BadRequestException;
import org.example.exception.ConflictException;
import org.example.exception.ForbiddenException;
import org.example.exception.NotFoundException;
import org.example.exception.UnauthorizedException;
import org.example.models.Credentials;
import org.example.models.UserWithCredentials;
import org.springframework.stereotype.Service;

@Service
public class GateawayService {

  public final AuthentificationProxy authentificationProxy;


  public GateawayService(AuthentificationProxy authentificationProxy) {
    this.authentificationProxy = authentificationProxy;
  }

  public void createOne(UserWithCredentials userWithCredentials)
      throws ConflictException, BadRequestException {
    try {
      authentificationProxy.createOne(userWithCredentials.getPseudo(),userWithCredentials);
    }catch (FeignException e){
      if (e.status() == 400) throw new BadRequestException();
      else if (e.status() == 409) throw new ConflictException();
      else throw e;
    }
  }

  public String connect(Credentials credentials) throws UnauthorizedException, BadRequestException {
    try {
     return authentificationProxy.connect(credentials);
    }catch (FeignException e){
      if(e.status()==401) throw new UnauthorizedException();
      if(e.status()==400) throw new BadRequestException();
      else throw e;
    }
  }

  public String verify(String token) throws UnauthorizedException {
    try {
     return authentificationProxy.verify(token);
    }catch (FeignException e){
      if(e.status()==401) throw new UnauthorizedException();
      else throw e;
    }
  }

  public void deleteOne(String pseudo) throws NotFoundException {
    try {
      authentificationProxy.deleteOne(pseudo);
    }catch (FeignException e){
      if(e.status()==404) throw new NotFoundException();
      else throw e;
    }
  }

  public void updateOne(UserWithCredentials credentials)
      throws NotFoundException, BadRequestException {
    try {
      authentificationProxy.updateOne(credentials.getPseudo(),credentials.toCredentials());
    }catch (FeignException e){
      if(e.status()==404) throw new NotFoundException();
      if (e.status() == 400) throw new BadRequestException();
      else throw e;
    }
  }
}
