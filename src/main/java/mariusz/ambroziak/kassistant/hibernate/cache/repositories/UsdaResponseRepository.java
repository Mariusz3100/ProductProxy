package mariusz.ambroziak.kassistant.hibernate.cache.repositories;

import mariusz.ambroziak.kassistant.webclients.usda.Usda_Response;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UsdaResponseRepository extends CrudRepository<Usda_Response,Long> {

    List<Usda_Response> findByQueryJson(String queryJson);


}
