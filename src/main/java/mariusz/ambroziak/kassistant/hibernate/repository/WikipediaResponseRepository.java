package mariusz.ambroziak.kassistant.hibernate.repository;

import mariusz.ambroziak.kassistant.webclients.morrisons.Morrisons_Response;
import mariusz.ambroziak.kassistant.webclients.wikipedia.Wikipedia_Response;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WikipediaResponseRepository extends CrudRepository<Wikipedia_Response,Long> {

    List<Wikipedia_Response> findByUrl(String url);


}
