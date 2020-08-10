package mariusz.ambroziak.kassistant.hibernate.parsing.repository;

import mariusz.ambroziak.kassistant.hibernate.parsing.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional
public class CustomPhraseConsideredRepositoryImpl implements CustomPhraseConsideredRepository {

    @Autowired
    @Lazy
    SavedTokenRepository savedTokenRepo;


    @Autowired
    @Lazy
    PhraseConsideredRepository phraseConsideredRepository;


    @Override
    public void save(SavedToken st) {
        savedTokenRepo.save(st);
    }

    @Override
    public void saveAllTokens(List<SavedToken> sts) {
        savedTokenRepo.saveAll(sts);
    }

    @Override
    public SavedToken saveIfNew(SavedToken st) {
        if(st==null) {
            return null;
        }else {
            List<SavedToken> byText = savedTokenRepo.findByText(st.getText());

            List<SavedToken> collect = byText.stream()
                    .filter(dbToken -> dbToken.equals(st))
                    .collect(Collectors.toList());


            if (collect.size() > 1) {
                System.err.println("Error, two same savedTokens: " + collect.stream().map(t -> t.getToken_id() + "").collect(Collectors.joining(" ")));
                return collect.get(0);
            } else if (collect.size() == 1) {
                return collect.get(0);
            } else {
                savedTokenRepo.save(st);
                return st;
            }

        }



    }

    @Override
    public void save(PhraseConsidered st) {
        if(st instanceof AdjacencyPhraseConsidered){
            save((AdjacencyPhraseConsidered)st);
        }else if(st instanceof DependencyPhraseConsidered){
            save((DependencyPhraseConsidered)st);

        }

    }

    public void save(AdjacencyPhraseConsidered st) {
        this.phraseConsideredRepository.save(st);
    }


    public void save(DependencyPhraseConsidered st) {
        st.setChild(saveIfNew(st.getChild()));
        st.setHead(saveIfNew(st.getHead()));

        phraseConsideredRepository.save(st);

    }

    @Override
    public void saveAllPhrases(List<PhraseConsidered> sts) {
        if(sts==null)
            return;


        for(PhraseConsidered sc:sts){
            this.save(sc);
        }
    }

    @Override
    public Iterable<PhraseConsidered> findAllPhrases() {
        return this.phraseConsideredRepository.findAll();
    }


}
