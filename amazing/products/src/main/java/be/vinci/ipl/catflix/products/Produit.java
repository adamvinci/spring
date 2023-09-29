package be.vinci.ipl.catflix.products;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "produits")
public class Produit {

    @Id
    private int id;
    private String name;
    private String categorie;
    private double price;
}
