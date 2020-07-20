package mariusz.ambroziak.kassistant.hibernate.repository;

import mariusz.ambroziak.kassistant.hibernate.model.PhraseFound;
import mariusz.ambroziak.kassistant.hibernate.model.PhraseFoundProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PhraseFoundProductTypeRepository extends JpaRepository<PhraseFoundProductType,Long> {



}
