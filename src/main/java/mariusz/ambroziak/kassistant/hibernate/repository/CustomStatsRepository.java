package mariusz.ambroziak.kassistant.hibernate.repository;

import mariusz.ambroziak.kassistant.hibernate.model.IngredientPhraseParsingResult;
import mariusz.ambroziak.kassistant.hibernate.model.PhraseFound;
import mariusz.ambroziak.kassistant.hibernate.model.ProductParsingResult;
import mariusz.ambroziak.kassistant.hibernate.model.Word;
import mariusz.ambroziak.kassistant.webclients.spacy.tokenization.Token;

import java.util.List;

public interface CustomStatsRepository {
    public Word getOrSave(String text,String lemma);

    public void saveIngredientStatsData(List<? extends Token> wordsToSave,IngredientPhraseParsingResult ingredientPhraseParsingResult);

    public void saveProductStatsData(List<? extends Token> wordsToSave,ProductParsingResult productParsingResult);


}
