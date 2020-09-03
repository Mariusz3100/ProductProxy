package mariusz.ambroziak.kassistant.hibernate.statistical.repository;

import mariusz.ambroziak.kassistant.hibernate.parsing.model.IngredientPhraseParsingResult;
import mariusz.ambroziak.kassistant.hibernate.parsing.model.ProductParsingResult;
import mariusz.ambroziak.kassistant.hibernate.statistical.model.Word;
import mariusz.ambroziak.kassistant.webclients.spacy.tokenization.Token;

import java.util.List;

public interface CustomStatsRepository {
    public Word getOrSave(String text,String lemma);

    public Word getOrSave(Token token);

    public void saveIngredientStatsData(List<? extends Token> wordsToSave,IngredientPhraseParsingResult ingredientPhraseParsingResult);
    public void saveIngredientIgnoredWordsData(List<? extends Token> wordsToSave,IngredientPhraseParsingResult productParsingResult);

    public void saveProductStatsData(List<? extends Token> wordsToSave,ProductParsingResult productParsingResult);
    public void saveProductIgnoredWordsData(List<? extends Token> wordsToSave,ProductParsingResult productParsingResult);


}
