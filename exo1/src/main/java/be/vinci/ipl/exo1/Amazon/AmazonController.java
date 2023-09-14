package be.vinci.ipl.exo1.Amazon;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AmazonController {
    private static final List<Produit> produits = new ArrayList<>();

    static {
        produits.add(new Produit(1, "produit1", "categorie1", 2.0));
        produits.add(new Produit(2, "produit2", "categorie2", 3.5));
        produits.add(new Produit(3, "produit3", "categorie1", 1.75));
        produits.add(new Produit(4, "produit4", "categorie3", 5.0));
        produits.add(new Produit(5, "produit5", "categorie2", 2.25));
    }

    @PostMapping("/produits")
    private ResponseEntity<Produit> createOne(@RequestBody Produit produit) {
        if (produits.stream().map(Produit::getId).toList().contains(produit.getId()))
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        produits.add(produit);
        return new ResponseEntity<>(produit, HttpStatus.CREATED);
    }

    @GetMapping("/produits")
    private Iterable<Produit> getAll() {
        return produits;
    }

    @GetMapping("/produits/{id}")
    private ResponseEntity<Produit> getOne(@PathVariable int id) {
        Produit produit = produits.stream().filter(produit1 -> produit1.getId() == id).findFirst().orElse(null);
        if (produit == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(produit, HttpStatus.ACCEPTED);

    }

    @DeleteMapping("/produits")
    private void deleteAll() {
        produits.clear();
    }

    @DeleteMapping("/produits/{id}")
    private ResponseEntity<Produit> deleteOne(@PathVariable int id) {
        int index = produits.stream().map(Produit::getId).toList().indexOf(id);
        if (index == -1)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Produit produit = produits.remove(index);
        return new ResponseEntity<>(produit, HttpStatus.ACCEPTED);
    }
    @PutMapping("/produits")
    private ResponseEntity<Produit> updateOne(@PathVariable int id, @RequestBody Produit produit){
        if (produit.getId() != id) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        int index = produits.stream().map(Produit::getId).toList().indexOf(produit.getId());
        if (index == -1) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Produit produit1 = produits.set(id,produit);
        return new ResponseEntity<>(produit1,HttpStatus.OK);
    }
}
