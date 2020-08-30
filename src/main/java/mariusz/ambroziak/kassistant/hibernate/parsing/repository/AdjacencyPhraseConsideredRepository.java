package mariusz.ambroziak.kassistant.hibernate.parsing.repository;

import mariusz.ambroziak.kassistant.enums.PhraseSourceType;
import mariusz.ambroziak.kassistant.hibernate.parsing.model.AdjacencyPhraseConsidered;
import mariusz.ambroziak.kassistant.hibernate.parsing.model.PhraseConsidered;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface AdjacencyPhraseConsideredRepository extends CrudRepository<AdjacencyPhraseConsidered,Long> {

    Iterable<AdjacencyPhraseConsidered> findAll();

    AdjacencyPhraseConsidered findById(long id);

    List<AdjacencyPhraseConsidered> findByPhraseContainingAndAcceptedTrue(String phrase);
    List<AdjacencyPhraseConsidered> findByPhraseContainingAndSourceAndAcceptedTrue(String phrase,PhraseSourceType phraseSourceType);

    List<AdjacencyPhraseConsidered> findByPhrase(String phrase);
    List<AdjacencyPhraseConsidered> findByPhraseAndSourceAndAcceptedTrue(String phrase, PhraseSourceType phraseSourceType);

}
