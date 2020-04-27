package mariusz.ambroziak.kassistant.hibernate.repository;

import mariusz.ambroziak.kassistant.hibernate.model.ProductLearningCase;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;


public interface ProductLearningCaseRepository  extends CrudRepository<ProductLearningCase,Long> {

    List<ProductLearningCase> findByUrl(String details_url);
    Iterable<ProductLearningCase> findAll();

    ProductLearningCase findById(long id);

}
