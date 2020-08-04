package mariusz.ambroziak.kassistant.hibernate.repository;

import mariusz.ambroziak.kassistant.hibernate.model.MatchExpected;
import mariusz.ambroziak.kassistant.hibernate.model.Word;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WordRepository extends CrudRepository<Word,Long> {
    List<Word> findByText(String text);

}
