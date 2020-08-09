package mariusz.ambroziak.kassistant.hibernate.parsing.repository;

import mariusz.ambroziak.kassistant.hibernate.parsing.model.MatchExpected;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MatchExpectedRepository extends CrudRepository<MatchExpected,Long> {
    List<MatchExpected> findByIngredient(String ingredientLine);
    List<MatchExpected> findByProduct(String product);

}
