package be.vinci.ipl.catflix.wishlist;

import be.vinci.ipl.catflix.wishlist.models.Wishlist;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListRepository extends CrudRepository<Wishlist,Integer> {
    Iterable<Wishlist> findByPseudo(String pseudo);

    boolean existsByProductIdAndPseudo(int ProductId, String pseudo);
    boolean existsByPseudo(String pseudo);
    @Transactional
    void deleteByPseudoAndProductId(String pseudo,int id);
    @Transactional
    void deleteByPseudo(String pseudo);
    @Transactional
    void deleteByProductId(int productId);
}
