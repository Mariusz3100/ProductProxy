package mariusz.ambroziak.kassistant.hibernate.repository;

import mariusz.ambroziak.kassistant.enums.ProductType;
import mariusz.ambroziak.kassistant.hibernate.model.PhraseFound;
import mariusz.ambroziak.kassistant.hibernate.model.PhraseFoundProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@Transactional
public class CustomPhraseFoundRepositoryImpl implements CustomPhraseFoundRepository {

    @Autowired
    @Lazy
    PhraseFoundRepository originalRepo;

    @Autowired
    PhraseFoundProductTypeRepository phraseFoundProductTypeRepository;

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
        System.out.println(pf.getPhrase());
        List<PhraseFound> byPhraseAndReasoning = originalRepo.findByPhrase(pf.getPhrase());

        if (byPhraseAndReasoning == null || byPhraseAndReasoning.size() == 0) {
            originalRepo.save(pf);
          //  originalRepo.flush();
        }else{
            if(byPhraseAndReasoning.size()>1){
                System.err.println("Two phrases of the same type found");
            }else{
                PhraseFound singleFromDb = byPhraseAndReasoning.get(0);

                Set<PhraseFoundProductType> typeFromDb = singleFromDb.getPhraseFoundProductType();
                Set<PhraseFoundProductType> argumentTypes = pf.getPhraseFoundProductType();


//                if(argumentTypes!=null&&!argumentTypes.isEmpty()) {
//                    for (PhraseFoundProductType phraseFoundProductType : argumentTypes) {
//                        if (phraseFoundProductType.getPfpt_id() == null) {
//                            this.phraseFoundProductTypeRepository.save(phraseFoundProductType);
//
//                        }
//
//                    }
//                }

               if( argumentTypes.stream().anyMatch(pfpt->!typeFromDb.contains(pfpt))){
                   Set<PhraseFoundProductType> toSave=argumentTypes.stream().filter(pfpt->!typeFromDb.contains(pfpt)).collect(Collectors.toSet());

                   toSave.forEach(pfpt->pfpt.setBasePhrase(singleFromDb));
                   typeFromDb.addAll(toSave);
                   this.originalRepo.save(singleFromDb);

       //            originalRepo.flush();
     //              this.phraseFoundProductTypeRepository.saveAll(toSave);
         //         this.phraseFoundProductTypeRepository.flush();


               }

                //List<PhraseFoundProductType> missing = typeFromDb.stream().filter(dbType -> !argumentTypes.contains(dbType)).collect(Collectors.toList());

//                if(typeFromDb.size()>pf.getPhraseFoundProductType().size()){
//                  for(PhraseFoundProductType phraseFoundProductType:typeFromDb){
//                      if(phraseFoundProductType.getPfpt_id()==null){
//                          this.phraseFoundProductTypeRepository.save(phraseFoundProductType);
//                      }
//                  }
////                  pf.setPhraseFoundProductType(typeFromDb);
////                  originalRepo.save(pf);
//                }


            }
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
