package mariusz.ambroziak.kassistant.hibernate.cache.repositories;

import mariusz.ambroziak.kassistant.hibernate.cache.model.Morrisons_Response;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MorrisonsResponseRepository extends CrudRepository<Morrisons_Response,Long> {

    List<Morrisons_Response> findByUrl(String url);
    List<Morrisons_Response> findByUrlStartingWith(String text);


}
