package mariusz.ambroziak.kassistant.hibernate.repository;

import mariusz.ambroziak.kassistant.webclients.edamam.nlp.Edaman_Nlp_Response;
import mariusz.ambroziak.kassistant.webclients.morrisons.Morrisons_Response;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EdamanResponseRepository extends CrudRepository<Edaman_Nlp_Response,Long> {

    List<Edaman_Nlp_Response> findByingredientLine(String ingredientLine);


}
