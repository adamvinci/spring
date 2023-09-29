package be.vinci.ipl.catflix.wishlist;

import be.vinci.ipl.catflix.wishlist.models.Produit;
import be.vinci.ipl.catflix.wishlist.models.User;
import be.vinci.ipl.catflix.wishlist.models.Wishlist;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.StreamSupport;

@Service
public class WishlistService {
    private final WishListRepository wishListRepository;
    private final ProduitProxy produitProxy;
    private final UserProxy  userProxy;

    public WishlistService(WishListRepository wishListRepository, ProduitProxy produitProxy, UserProxy userProxy) {
        this.wishListRepository = wishListRepository;
        this.produitProxy = produitProxy;
        this.userProxy = userProxy;
    }
    public Iterable<Wishlist> getAll(){
       return wishListRepository.findAll();
    }
    public Iterable<Produit> getProductFromUser(String pseudo){
        Iterable<Wishlist> listUser = wishListRepository.findByPseudo(pseudo);
        return StreamSupport.stream(listUser.spliterator(), false)
                .map(wishlist -> {
                    int idProduct = wishlist.getProductId();
                    return produitProxy.getOne(idProduct);
                })
                .toList();
    }
    public User getOneUser(String pseudo){
 return userProxy.getOne(pseudo);
    }
    public boolean createProduct(int produit,String pseudo){
        if(Objects.equals(produitProxy.getOne(produit), ResponseEntity.notFound()))
            return false;
        if(wishListRepository.existsByProductIdAndPseudo(produit,pseudo))
            return false;
        Wishlist wishlist = new Wishlist();
        wishlist.setPseudo(pseudo);
        wishlist.setProductId(produit);
        wishListRepository.save(wishlist);
        return true;
    }
    public boolean deleteOne(String pseudo,int id){
        if(!wishListRepository.existsByProductIdAndPseudo(id, pseudo))
            return false;
        wishListRepository.deleteByPseudoAndProductId(pseudo,id);
        return true;
    }
    public void deleteAll(String pseudo){
        wishListRepository.deleteByPseudo(pseudo);
    }
    public void deleteAllProduct(int id){
        wishListRepository.deleteByProductId(id);
    }
    public Produit getOneProduct(int productid){
        return produitProxy.getOne(productid);
    }
}
