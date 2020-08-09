package mariusz.ambroziak.kassistant.hibernate.cache.repositories;

import mariusz.ambroziak.kassistant.webclients.webknox.WebknoxResponse;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WebknoxResponseRepository extends CrudRepository<WebknoxResponse,Long> {

    List<WebknoxResponse> findByUrl(String url);
    List<WebknoxResponse> findByQuery(String query);


}
