package mariusz.ambroziak.kassistant.hibernate.repository;

import mariusz.ambroziak.kassistant.hibernate.model.ProductLearningCase;
import mariusz.ambroziak.kassistant.hibernate.model.ProductParsingResult;
import mariusz.ambroziak.kassistant.webclients.tesco.Tesco_Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductParsingResultRepository  extends CrudRepository<ProductParsingResult,Long> {


}
