package mariusz.ambroziak.kassistant.hibernate.repository;

import mariusz.ambroziak.kassistant.hibernate.model.ProductData;
import mariusz.ambroziak.kassistant.webclients.tesco.Tesco_Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

public interface TescoProductRepository  extends ProductRepository<Tesco_Product> {


}
