package be.vinci.ipl.catflix.authentification;

import be.vinci.ipl.catflix.authentification.models.SafeCredentials;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthentificationRepository extends CrudRepository<SafeCredentials, String> {

}
