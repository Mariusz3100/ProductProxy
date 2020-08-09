package mariusz.ambroziak.kassistant.hibernate.parsing.repository;

import mariusz.ambroziak.kassistant.hibernate.parsing.model.ProductLearningCase;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ProductLearningCaseRepository  extends CrudRepository<ProductLearningCase,Long> {

    List<ProductLearningCase> findByUrl(String details_url);
    Iterable<ProductLearningCase> findAll();

    ProductLearningCase findById(long id);

}
