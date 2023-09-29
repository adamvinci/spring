package be.vinci.ipl.catflix.wishlist;

import be.vinci.ipl.catflix.wishlist.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Repository

@FeignClient(name = "users")
public interface UserProxy {
    @GetMapping("/users/{pseudo}")
    User getOne(@PathVariable String pseudo);
}
