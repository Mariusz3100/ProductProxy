package mariusz.ambroziak.kassistant.hibernate.statistical.repository;

import mariusz.ambroziak.kassistant.hibernate.parsing.model.*;
import mariusz.ambroziak.kassistant.hibernate.statistical.model.*;
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

    @Autowired
    IgnoredIngredientWordOccurenceRepository ignoredIngredientWordOccurenceRepository;

    @Autowired
    IgnoredProductWordOccurenceRepository ignoredProductWordOccurenceRepository;




    @Override
    public Word getOrSave(String text, String lemma) {

        if(text==null||text.isEmpty())
            System.err.println("Empty text for Word class provided");

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

    @Override
    public Word getOrSave(Token token) {
        if(token==null||token.getText().isEmpty())
            System.err.println("Empty token for Word class provided");

        if(!Pattern.matches(lettersRegex,token.getText()))
            System.err.println("Text for Word class contains illegal characters: "+ token.getText());



        List<Word> retrieved=wordRepository.findByText(token.getText());

        if(retrieved==null||retrieved.isEmpty()){
            Word toSave=new Word(token);
            this.wordRepository.save(toSave);
            return toSave;

        }else {
            return retrieved.get(0);
        }
    }


    public void saveIngredientStatsData(List<? extends Token> wordsToSave, IngredientPhraseParsingResult ingredientPhraseParsingResult) {
        if(ingredientPhraseParsingResult==null||ingredientPhraseParsingResult.getOriginalName()==null)
            System.err.println("null ingredientPhraseParsingResult for stats provided");

        if(wordsToSave==null||wordsToSave.isEmpty())
            System.err.println("Empty word list for stats provided for phrase: "+ingredientPhraseParsingResult.getOriginalName());

        for(Token token:wordsToSave){
            try {
                Word savedWord = this.getOrSave(token);

                IngredientWordOccurence ingredientWordOccurence = new IngredientWordOccurence(savedWord, ingredientPhraseParsingResult);
                this.ingredientWordOccurenceRepository.save(ingredientWordOccurence);
            }catch (Exception e){
                e.printStackTrace();

            }

        }

    }




    public void saveProductStatsData(List<? extends Token> wordsToSave,ProductParsingResult productParsingResult) {
        if(productParsingResult==null||productParsingResult.getOriginalName()==null)
            System.err.println("null ingredientPhraseParsingResult for stats provided");

        if(wordsToSave==null||wordsToSave.isEmpty())
           System.err.println("Empty word list for stats provided for phrase: "+productParsingResult.getOriginalName());

        for(Token token:wordsToSave){
            try{
                Word savedWord=this.getOrSave(token);

                ProductWordOccurence productWordOccurence=new ProductWordOccurence(savedWord,productParsingResult);
                this.productWordOccurenceRepository.save(productWordOccurence);
            }catch (Exception e){
                e.printStackTrace();

            }

        }
    }

    @Override
    public void saveProductIgnoredWordsData(List<? extends Token> wordsToSave, ProductParsingResult productParsingResult) {
        if(productParsingResult==null||productParsingResult.getOriginalName()==null)
            System.err.println("null ingredientPhraseParsingResult for stats provided");

        if(wordsToSave==null||wordsToSave.isEmpty())
            System.err.println("Empty word list for stats provided for phrase: "+productParsingResult.getOriginalName());

        for(Token token:wordsToSave){
            try{
                Word savedWord=this.getOrSave(token);

                IgnoredProductWordOccurence productWordOccurence=new IgnoredProductWordOccurence(savedWord,productParsingResult);
                this.ignoredProductWordOccurenceRepository.save(productWordOccurence);
            }catch (Exception e){
                e.printStackTrace();

            }

        }
    }

    @Override
    public void saveIngredientIgnoredWordsData(List<? extends Token> wordsToSave, IngredientPhraseParsingResult ingredientPhraseParsingResult) {
        if(ingredientPhraseParsingResult==null||ingredientPhraseParsingResult.getOriginalName()==null)
            System.err.println("null ingredientPhraseParsingResult for stats provided");

        if(wordsToSave==null||wordsToSave.isEmpty())
            System.err.println("Empty word list for stats provided for phrase: "+ingredientPhraseParsingResult.getOriginalName());

        for(Token token:wordsToSave){
            try{
                Word savedWord=this.getOrSave(token);

                IgnoredIngredientWordOccurence productWordOccurence=new IgnoredIngredientWordOccurence(savedWord,ingredientPhraseParsingResult);
                this.ignoredIngredientWordOccurenceRepository.save(productWordOccurence);
            }catch (Exception e){
                e.printStackTrace();

            }

        }
    }
}
