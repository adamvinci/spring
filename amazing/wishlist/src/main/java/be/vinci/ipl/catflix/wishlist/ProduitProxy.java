package be.vinci.ipl.catflix.wishlist;

import be.vinci.ipl.catflix.wishlist.models.Produit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
@FeignClient(name = "products")
public interface ProduitProxy {

    @GetMapping("/produits/{id}")
    Produit getOne(@PathVariable int id);
}
