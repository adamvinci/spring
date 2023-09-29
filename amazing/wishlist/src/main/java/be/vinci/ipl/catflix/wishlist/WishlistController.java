package be.vinci.ipl.catflix.wishlist;


import be.vinci.ipl.catflix.wishlist.models.Produit;
import be.vinci.ipl.catflix.wishlist.models.Wishlist;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.StreamSupport;

@RestController
public class WishlistController {
    private final WishlistService wishlistService;
    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }
    @GetMapping("/wishlist/user/{pseudo}")
    public Iterable<Produit> getWishListOfUser(@PathVariable String pseudo){
        return wishlistService.getProductFromUser(pseudo);
    }
    @GetMapping("/wishlist")
    public Iterable<Wishlist> getAllWishes(){
        return wishlistService.getAll();
    }
    @DeleteMapping("/wishlist/{pseudo}/{productId}")
    public ResponseEntity<Void> deleteProductForUser(@PathVariable String pseudo,@PathVariable int productId){
        if(!wishlistService.deleteOne(pseudo,productId))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/wishlist/{pseudo}")
    public ResponseEntity<Void> deleteAllFromUser(@PathVariable String pseudo){
        wishlistService.deleteAll(pseudo);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/wishlist/allWishes/{productId}")
    public ResponseEntity<Void> deleteAllWishWithProductid(@PathVariable int productId){
        wishlistService.deleteAllProduct(productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/wishlist/{pseudo}/{produit}")
    public ResponseEntity<Void> createProduct(@PathVariable String pseudo,@PathVariable int produit){
        if(wishlistService.getOneProduct(produit).equals(ResponseEntity.notFound()) ) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(wishlistService.getOneUser(pseudo).equals(ResponseEntity.notFound())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(!wishlistService.createProduct(produit, pseudo)){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
