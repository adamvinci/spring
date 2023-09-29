package be.vinci.ipl.catflix.wishlist.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Produit {

    private int id;
    private String name;
    private String categorie;
    private double price;
}
