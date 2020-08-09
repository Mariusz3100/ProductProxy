package mariusz.ambroziak.kassistant.hibernate.cache.repositories;

import mariusz.ambroziak.kassistant.hibernate.cache.model.Edaman_Nlp_Response;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EdamanResponseRepository extends CrudRepository<Edaman_Nlp_Response,Long> {

    List<Edaman_Nlp_Response> findByingredientLine(String ingredientLine);


}
