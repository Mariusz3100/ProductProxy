package mariusz.ambroziak.kassistant.hibernate.repository;

import mariusz.ambroziak.kassistant.hibernate.model.PhraseFound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PhraseFoundRepository extends JpaRepository<PhraseFound,Long> {

    List<PhraseFound> findByPhrase(String phrase);
    List<PhraseFound> findByPhraseContaining(String phrase);
    List<PhraseFound> findByReasoning(String reasoning);
    List<PhraseFound> findByPhraseAndReasoning(String phrase,String reasoning);

}
