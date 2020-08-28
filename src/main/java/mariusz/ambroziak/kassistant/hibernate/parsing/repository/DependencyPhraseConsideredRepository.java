package mariusz.ambroziak.kassistant.hibernate.parsing.repository;

import mariusz.ambroziak.kassistant.hibernate.parsing.model.AdjacencyPhraseConsidered;
import mariusz.ambroziak.kassistant.hibernate.parsing.model.DependencyPhraseConsidered;
import mariusz.ambroziak.kassistant.hibernate.parsing.model.SavedToken;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface DependencyPhraseConsideredRepository extends CrudRepository<DependencyPhraseConsidered,Long> {

    Iterable<DependencyPhraseConsidered> findAll();

    AdjacencyPhraseConsidered findById(long id);

    List<DependencyPhraseConsidered> findByHeadAndAcceptedTrueOrChildAndAcceptedTrue(SavedToken head, SavedToken child);
    List<DependencyPhraseConsidered> findByHeadAndChild(SavedToken head, SavedToken child);

}
