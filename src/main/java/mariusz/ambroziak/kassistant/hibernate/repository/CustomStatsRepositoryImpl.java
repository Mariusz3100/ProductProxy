package mariusz.ambroziak.kassistant.hibernate.repository;

import mariusz.ambroziak.kassistant.hibernate.model.*;
import mariusz.ambroziak.kassistant.webclients.spacy.tokenization.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Pattern;

@Repository
@Transactional
public class CustomStatsRepositoryImpl implements CustomStatsRepository {
    public static final String lettersRegex="[a-zA-Z]+";

    @Autowired
    WordRepository wordRepository;

    @Autowired
    IngredientWordOccurenceRepository ingredientWordOccurenceRepository;
    @Autowired
    ProductWordOccurenceRepository productWordOccurenceRepository;
    @Override
    public Word getOrSave(String text,String lemma) {

        if(text==null||text.isEmpty())
            throw new IllegalArgumentException("Empty text for word provided");

        if(!Pattern.matches(lettersRegex,text))
            throw new IllegalArgumentException("Text for word contains illegal characters: "+text);



        List<Word> retrieved=wordRepository.findByText(text);

        if(retrieved==null||retrieved.isEmpty()){
            Word toSave=new Word(text,lemma);
            this.wordRepository.save(toSave);
            return toSave;

        }else {
            return retrieved.get(0);
        }

    }


    public void saveIngredientStatsData(List<? extends Token> wordsToSave, IngredientPhraseParsingResult ingredientPhraseParsingResult) {


        if(wordsToSave==null||wordsToSave.isEmpty()||ingredientPhraseParsingResult==null)
            throw new IllegalArgumentException("Empty word list for stats provided");

        for(Token token:wordsToSave){
            Word savedWord=this.getOrSave(token.getText(),token.getLemma());

            IngredientWordOccurence ingredientWordOccurence=new IngredientWordOccurence(savedWord,ingredientPhraseParsingResult);
            this.ingredientWordOccurenceRepository.save(ingredientWordOccurence);

        }

    }


    public void saveProductStatsData(List<? extends Token> wordsToSave,ProductParsingResult productParsingResult) {
        if(wordsToSave==null||wordsToSave.isEmpty()||productParsingResult==null)
            throw new IllegalArgumentException("Empty word list for stats provided");

        for(Token token:wordsToSave){
            Word savedWord=this.getOrSave(token.getText(),token.getLemma());

            ProductWordOccurence productWordOccurence=new ProductWordOccurence(savedWord,productParsingResult);
            this.productWordOccurenceRepository.save(productWordOccurence);

        }
    }
}
