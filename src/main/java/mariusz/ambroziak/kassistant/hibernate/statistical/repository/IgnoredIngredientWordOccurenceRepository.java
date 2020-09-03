package mariusz.ambroziak.kassistant.hibernate.statistical.repository;

import mariusz.ambroziak.kassistant.hibernate.statistical.model.IgnoredIngredientWordOccurence;
import mariusz.ambroziak.kassistant.hibernate.statistical.model.IngredientWordOccurence;
import org.springframework.data.repository.CrudRepository;

public interface IgnoredIngredientWordOccurenceRepository extends CrudRepository<IgnoredIngredientWordOccurence,Long> {


}
