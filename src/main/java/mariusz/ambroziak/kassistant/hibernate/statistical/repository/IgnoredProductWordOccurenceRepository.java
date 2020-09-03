package mariusz.ambroziak.kassistant.hibernate.statistical.repository;

import mariusz.ambroziak.kassistant.hibernate.statistical.model.IgnoredProductWordOccurence;
import mariusz.ambroziak.kassistant.hibernate.statistical.model.ProductWordOccurence;
import org.springframework.data.repository.CrudRepository;

public interface IgnoredProductWordOccurenceRepository extends CrudRepository<IgnoredProductWordOccurence,Long> {

}
