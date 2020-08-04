package mariusz.ambroziak.kassistant.hibernate.repository;

import mariusz.ambroziak.kassistant.hibernate.model.ProductWordOccurence;
import mariusz.ambroziak.kassistant.hibernate.model.Word;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductWordOccurenceRepository extends CrudRepository<ProductWordOccurence,Long> {

}
