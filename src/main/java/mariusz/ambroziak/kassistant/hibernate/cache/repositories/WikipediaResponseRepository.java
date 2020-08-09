package mariusz.ambroziak.kassistant.hibernate.cache.repositories;

import mariusz.ambroziak.kassistant.webclients.wikipedia.Wikipedia_Response;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WikipediaResponseRepository extends CrudRepository<Wikipedia_Response,Long> {

    List<Wikipedia_Response> findByUrl(String url);


}
