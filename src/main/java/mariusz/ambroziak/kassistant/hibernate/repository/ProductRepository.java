package mariusz.ambroziak.kassistant.hibernate.repository;

import mariusz.ambroziak.kassistant.hibernate.model.ProductData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface ProductRepository<T extends ProductData>  extends CrudRepository<T,Long> {

    List<T> findByUrl(String details_url);
    List<T> findAll();
    List<T> findByName(String name);
    T findById(long id);

}
