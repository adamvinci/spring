package be.vinci.ipl.catflix.products;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class ProductController {

    private final ProduitService produits;

    public ProductController(ProduitService produits) {
        this.produits = produits;
    }

    @PostMapping("/produits")
    private ResponseEntity<Produit> createOne(@RequestBody Produit produit) {
        boolean created = produits.createOne(produit);
        if(!created)
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        return new ResponseEntity<>(produit, HttpStatus.CREATED);
    }

    @GetMapping("/produits")
    private Iterable<Produit> getAll() {
        return produits.getAll();
    }

    @GetMapping("/produits/{id}")
    private ResponseEntity<Produit> getOne(@PathVariable int id) {
        System.out.println("getone");
        Produit produit = produits.getOne(id);
        if (produit == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(produit, HttpStatus.ACCEPTED);

    }

    @DeleteMapping("/produits")
    private void deleteAll() {
        produits.deleteAll();
    }

    @DeleteMapping("/produits/{id}")
    private ResponseEntity<Produit> deleteOne(@PathVariable int id) {
        Produit produit=produits.deleteOne(id);
        if (produit == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(produit, HttpStatus.ACCEPTED);
    }
    @PutMapping("/produits/{id}")
    private ResponseEntity<Produit> updateOne(@PathVariable int id, @RequestBody Produit produit){
        if (produit.getId() != id) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Produit produit1 = produits.updateOne(id,produit);
        if (produit1 ==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(produit1,HttpStatus.OK);
    }
}
