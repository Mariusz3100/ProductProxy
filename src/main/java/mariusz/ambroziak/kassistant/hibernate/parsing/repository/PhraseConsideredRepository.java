package mariusz.ambroziak.kassistant.hibernate.parsing.repository;

import mariusz.ambroziak.kassistant.hibernate.parsing.model.IngredientLearningCase;
import mariusz.ambroziak.kassistant.hibernate.parsing.model.PhraseConsidered;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface PhraseConsideredRepository extends CrudRepository<PhraseConsidered,Long> {

    Iterable<PhraseConsidered> findAll();

    PhraseConsidered findById(long id);


}
