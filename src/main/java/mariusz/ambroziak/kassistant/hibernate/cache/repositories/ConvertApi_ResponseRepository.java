package mariusz.ambroziak.kassistant.hibernate.cache.repositories;

import mariusz.ambroziak.kassistant.hibernate.cache.model.Morrisons_Response;
import mariusz.ambroziak.kassistant.webclients.convert.ConvertApi_Response;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ConvertApi_ResponseRepository extends CrudRepository<ConvertApi_Response,Long> {

    List<ConvertApi_Response> findByInputTypeAndOutputType(String inputType,String outputType);



}
