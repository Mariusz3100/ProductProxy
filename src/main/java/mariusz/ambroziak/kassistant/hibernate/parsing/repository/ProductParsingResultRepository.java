package mariusz.ambroziak.kassistant.hibernate.parsing.repository;

import mariusz.ambroziak.kassistant.hibernate.parsing.model.ProductParsingResult;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductParsingResultRepository  extends CrudRepository<ProductParsingResult,Long> {

    List<ProductParsingResult> findByOriginalName(String originalName);
}
