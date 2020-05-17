package mariusz.ambroziak.kassistant.hibernate.repository;

import mariusz.ambroziak.kassistant.hibernate.model.IngredientPhraseParsingResult;
import mariusz.ambroziak.kassistant.hibernate.model.ParsingBatch;
import org.springframework.data.repository.CrudRepository;

public interface ParsingBatchRepository extends CrudRepository<ParsingBatch,Long> {


}
