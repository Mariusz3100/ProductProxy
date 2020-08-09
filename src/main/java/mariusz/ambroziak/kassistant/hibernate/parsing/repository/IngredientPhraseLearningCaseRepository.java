package mariusz.ambroziak.kassistant.hibernate.parsing.repository;

import mariusz.ambroziak.kassistant.hibernate.parsing.model.IngredientLearningCase;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface IngredientPhraseLearningCaseRepository extends CrudRepository<IngredientLearningCase,Long> {

    Iterable<IngredientLearningCase> findAll();

    IngredientLearningCase findById(long id);
    List<IngredientLearningCase> findByOriginalPhrase(String originalPhrase);


}
