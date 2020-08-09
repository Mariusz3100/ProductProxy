package mariusz.ambroziak.kassistant.hibernate.parsing.repository;

import mariusz.ambroziak.kassistant.webclients.morrisons.Morrisons_Product;

import java.util.List;


public interface MorrisonProductRepository extends ProductRepository<Morrisons_Product> {
    List<Morrisons_Product> findByNameContaining(String phrase);


}
