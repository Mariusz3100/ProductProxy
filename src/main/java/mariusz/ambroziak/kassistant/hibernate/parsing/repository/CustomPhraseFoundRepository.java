package mariusz.ambroziak.kassistant.hibernate.parsing.repository;

import mariusz.ambroziak.kassistant.hibernate.parsing.model.PhraseFound;

import java.util.List;

public interface CustomPhraseFoundRepository {

    void save(PhraseFound pf);
    void saveAll(List<PhraseFound> pfs);


    void saveIfNew(PhraseFound pf);
    void saveAllIfNew(List<PhraseFound> pfs);
    List<PhraseFound> findByPhrase(String phrase);
    List<PhraseFound> findByPhraseContaining(String phrase);
    List<PhraseFound> findByReasoning(String reasoning);
    List<PhraseFound> findByPhraseAndReasoning(String phrase,String reasoning);



    List<PhraseFound> findBySingleWordPhrase(String word);


}
