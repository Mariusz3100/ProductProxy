package mariusz.ambroziak.kassistant.hibernate.repository;

import mariusz.ambroziak.kassistant.hibernate.model.MatchExpected;
import mariusz.ambroziak.kassistant.hibernate.model.MatchFound;
import mariusz.ambroziak.kassistant.webclients.edamam.nlp.Edaman_Nlp_Response;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MatchExpectedRepository extends CrudRepository<MatchExpected,Long> {
    List<MatchExpected> findByIngredient(String ingredientLine);
    List<MatchExpected> findByProduct(String product);

}
