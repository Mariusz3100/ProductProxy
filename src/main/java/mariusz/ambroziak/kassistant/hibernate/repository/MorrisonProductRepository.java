package mariusz.ambroziak.kassistant.hibernate.repository;

import mariusz.ambroziak.kassistant.hibernate.model.PhraseFound;
import mariusz.ambroziak.kassistant.webclients.morrisons.Morrisons_Product;
import mariusz.ambroziak.kassistant.webclients.tesco.Tesco_Product;

import java.util.List;


public interface MorrisonProductRepository extends ProductRepository<Morrisons_Product> {
    List<Morrisons_Product> findByNameContaining(String phrase);


}
