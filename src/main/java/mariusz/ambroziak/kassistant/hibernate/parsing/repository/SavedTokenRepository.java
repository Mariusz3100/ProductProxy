package mariusz.ambroziak.kassistant.hibernate.parsing.repository;

import mariusz.ambroziak.kassistant.hibernate.parsing.model.PhraseConsidered;
import mariusz.ambroziak.kassistant.hibernate.parsing.model.PhraseFound;
import mariusz.ambroziak.kassistant.hibernate.parsing.model.SavedToken;
import mariusz.ambroziak.kassistant.pojos.QualifiedToken;
import mariusz.ambroziak.kassistant.webclients.spacy.tokenization.Token;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface SavedTokenRepository extends CrudRepository<SavedToken,Long> {

    Iterable<SavedToken> findAll();

    SavedToken findById(long id);
    List<SavedToken> findByText(String text);
    List<SavedToken> findByLemma(String text);


}
