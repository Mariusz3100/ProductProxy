package mariusz.ambroziak.kassistant.hibernate.parsing.repository;

import mariusz.ambroziak.kassistant.hibernate.parsing.model.PhraseConsidered;
import mariusz.ambroziak.kassistant.hibernate.parsing.model.PhraseFound;
import mariusz.ambroziak.kassistant.hibernate.parsing.model.SavedToken;

import java.util.List;

public interface CustomPhraseConsideredRepository {

    void save(SavedToken st);
    void saveAllTokens(List<SavedToken> sts);


    SavedToken saveIfNew(SavedToken st);

    void save(PhraseConsidered st);
    void saveAllPhrases(List<PhraseConsidered> sts);


    Iterable<PhraseConsidered> findAllPhrases();

    Iterable<PhraseConsidered> findPhrasesCompatible(String word);



}
