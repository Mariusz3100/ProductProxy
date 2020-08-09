package mariusz.ambroziak.kassistant.hibernate.repository;

import mariusz.ambroziak.kassistant.webclients.morrisons.Morrisons_Response;
import mariusz.ambroziak.kassistant.webclients.webknox.WebknoxResponse;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WebknoxResponseRepository extends CrudRepository<WebknoxResponse,Long> {

    List<WebknoxResponse> findByUrl(String url);
    List<WebknoxResponse> findByQuery(String query);


}
