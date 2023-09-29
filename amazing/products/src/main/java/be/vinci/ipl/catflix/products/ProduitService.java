package be.vinci.ipl.catflix.products;

import org.springframework.stereotype.Service;

@Service
public class ProduitService {

    private final ProduitRepository produitRepository;

    public ProduitService(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    public boolean createOne(Produit produit){
        if(produitRepository.existsById(produit.getId()))
            return false;
        produitRepository.save(produit);
        return true;
    }
    public Iterable<Produit> getAll(){
        return produitRepository.findAll();
    }
    public Produit getOne(int id){
        return produitRepository.findById(id).orElse(null);
    }
    public void deleteAll(){
        produitRepository.deleteAll();
    }
    public Produit deleteOne(int id){
        Produit produit=produitRepository.findById(id).orElse(null);
        if(produit == null)
            return null;
        produitRepository.deleteById(id);
        return produit;
    }
    public Produit updateOne(int id,Produit produit){
        Produit produitExist = produitRepository.findById(id).orElse(null);
        if(produitExist == null)
            return null;
        produitRepository.save(produit);
        return produit;
    }
}
