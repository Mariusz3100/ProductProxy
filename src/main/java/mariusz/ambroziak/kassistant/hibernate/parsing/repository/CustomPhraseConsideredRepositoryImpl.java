package mariusz.ambroziak.kassistant.hibernate.parsing.repository;

import mariusz.ambroziak.kassistant.enums.PhraseSourceType;
import mariusz.ambroziak.kassistant.hibernate.parsing.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
    @Autowired
    @Lazy
    AdjacencyPhraseConsideredRepository adjacencyPhraseConsideredRepository;

    @Autowired
    @Lazy
    DependencyPhraseConsideredRepository dependencyPhraseConsideredRepository;
    @Autowired
    @Lazy
    SavedTokenRepository savedTokenRepository;




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
        if(st.getSource()==null)
        {
            if(st.getIngredientPhraseParsingResult()!=null)st.setSource(PhraseSourceType.Ingredient);
            if(st.getProductParsingResult()!=null)st.setSource(PhraseSourceType.Product);
        }

        if(st instanceof AdjacencyPhraseConsidered){
            saveIfNew((AdjacencyPhraseConsidered)st);
        }else if(st instanceof DependencyPhraseConsidered){
            saveIfNew((DependencyPhraseConsidered)st);

        }

    }

    public void save(AdjacencyPhraseConsidered st) {
        this.phraseConsideredRepository.save(st);
    }

    public void saveIfNew(AdjacencyPhraseConsidered st) {
        List<AdjacencyPhraseConsidered> byPhraseFiltered =
                this.adjacencyPhraseConsideredRepository
                        .findByPhrase(st.getPhrase())
                        .stream()
                        .filter(adjacencyPhraseConsidered -> Objects.equals(adjacencyPhraseConsidered.getSource(),st.getSource()))
                        .collect(Collectors.toList());

        if(byPhraseFiltered.isEmpty()){
            this.phraseConsideredRepository.save(st);
        }else if(byPhraseFiltered.size()>1){
            System.err.println("Two AdjacencyPhraseConsidered with same phrase: "+st.getPhrase()+", check constraints.");
        }


    }


    public void saveIfNew(DependencyPhraseConsidered st) {
        st.setChild(saveIfNew(st.getChild()));
        st.setHead(saveIfNew(st.getHead()));


        List<DependencyPhraseConsidered> byHeadAndChild = dependencyPhraseConsideredRepository.findByHeadAndChild(st.getHead(), st.getChild());

        boolean exists = byHeadAndChild.stream()
                .filter(dependencyPhraseConsidered -> Objects.equals(dependencyPhraseConsidered.getSource(), st.getSource()))
                .count() > 0;

        if(!exists){
              phraseConsideredRepository.save(st);
        }







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

    @Override
    public Iterable<PhraseConsidered> findPhrasesCompatible(String word) {
        List<AdjacencyPhraseConsidered> byPhraseContaining = this.adjacencyPhraseConsideredRepository.findByPhraseContainingAndAcceptedTrue(word);

        List<SavedToken> byText = savedTokenRepository.findByText(word);
        byText.addAll(savedTokenRepository.findByLemma(word));


        Set<SavedToken> setOfTokens = byText.stream().collect(Collectors.toSet());

        Set<DependencyPhraseConsidered> byTokens=new HashSet<>();

        setOfTokens.forEach(savedToken ->
        {
            byTokens.addAll(this.dependencyPhraseConsideredRepository.findByHeadAndAcceptedTrueOrChildAndAcceptedTrue(savedToken,savedToken));

        });

        List<PhraseConsidered> retValue=new ArrayList<>();

        retValue.addAll(byPhraseContaining);
        retValue.addAll(byTokens.stream().collect(Collectors.toSet()));

        return retValue;


    }


}
