package mariusz.ambroziak.kassistant.hibernate.repository;

import mariusz.ambroziak.kassistant.hibernate.model.PhraseFound;
import mariusz.ambroziak.kassistant.webclients.morrisons.Morrisons_Response;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MorrisonsResponseRepository extends CrudRepository<Morrisons_Response,Long> {

    List<Morrisons_Response> findByUrl(String url);


}
