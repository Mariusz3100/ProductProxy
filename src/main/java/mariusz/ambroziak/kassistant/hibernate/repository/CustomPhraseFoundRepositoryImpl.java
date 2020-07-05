package mariusz.ambroziak.kassistant.hibernate.repository;

import mariusz.ambroziak.kassistant.enums.ProductType;
import mariusz.ambroziak.kassistant.hibernate.model.PhraseFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional
public class CustomPhraseFoundRepositoryImpl implements CustomPhraseFoundRepository {

    @Autowired
    @Lazy
    PhraseFoundRepository originalRepo;

    @Override
    public void save(PhraseFound pf) {
        originalRepo.save(pf);
    }

    @Override
    public void saveAll(List<PhraseFound> pfs) {
        originalRepo.saveAll(pfs);
    }

    @Override
    public void saveIfNew(PhraseFound pf) {
        if (pf == null)
            return;

        List<PhraseFound> byPhraseAndReasoning = originalRepo.findByPhrase(pf.getPhrase());

        if (byPhraseAndReasoning == null || byPhraseAndReasoning.size() == 0) {
            originalRepo.save(pf);
        }else if((pf.getProductType()!=null&&!pf.getProductType().equals(ProductType.unknown))){
            List<PhraseFound> toSave= byPhraseAndReasoning.stream()
                    .filter(dbPhrase -> dbPhrase.getProductType() == null || dbPhrase.getProductType().equals(ProductType.unknown))
                    .collect(Collectors.toList());
            toSave.forEach(phraseFound -> phraseFound.setProductType(pf.getProductType()));
            saveAll(toSave);

        }
    }

    @Override
    public void saveAllIfNew(List<PhraseFound> pfs) {
        if(pfs==null)
            return;


        for(PhraseFound pf:pfs){
            this.saveIfNew(pf);
        }
    }

    @Override
    public List<PhraseFound> findByPhrase(String phrase) {
        return this.originalRepo.findByPhrase(phrase);
    }

    @Override
    public List<PhraseFound> findByPhraseContaining(String phrase) {
        return this.originalRepo.findByPhraseContaining(phrase);
    }

    @Override
    public List<PhraseFound> findByReasoning(String reasoning) {
        return this.originalRepo.findByReasoning(reasoning);
    }

    @Override
    public List<PhraseFound> findByPhraseAndReasoning(String phrase, String reasoning) {
        return originalRepo.findByPhraseAndReasoning(phrase,reasoning);
    }

    @Override
    public List<PhraseFound> findBySingleWordPhrase(String word) {

        if(word==null||word.isEmpty())
            return new ArrayList<>();
        word=word.trim();
        if(word.contains(" "))
            System.err.println("Two words passed to one word method: "+word);


        List<PhraseFound> byPhrase = findByPhrase(word);

        if(byPhrase.size()>1)
            System.err.println("Two results for word: "+word);

        return byPhrase;
    }


}
