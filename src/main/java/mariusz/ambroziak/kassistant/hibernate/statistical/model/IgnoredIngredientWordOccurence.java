package mariusz.ambroziak.kassistant.hibernate.statistical.model;

import mariusz.ambroziak.kassistant.hibernate.parsing.model.IngredientPhraseParsingResult;

import javax.persistence.*;

@Entity
@Table(name = "IgnoredIngredientWordOccurence",schema = "statistics")
public class IgnoredIngredientWordOccurence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long iiwo_id;

    @ManyToOne
    private Word word;


    @OneToOne
    IngredientPhraseParsingResult ingredientPhraseParsingResult;


    public Long getIiwo_id() {
        return iiwo_id;
    }

    public void setIiwo_id(Long iiwo_id) {
        this.iiwo_id = iiwo_id;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public IngredientPhraseParsingResult getIngredientPhraseParsingResult() {
        return ingredientPhraseParsingResult;
    }

    public void setIngredientPhraseParsingResult(IngredientPhraseParsingResult ingredientPhraseParsingResult) {
        this.ingredientPhraseParsingResult = ingredientPhraseParsingResult;
    }

    public IgnoredIngredientWordOccurence(Word word, IngredientPhraseParsingResult ingredientPhraseParsingResult) {
        this.word = word;
        this.ingredientPhraseParsingResult = ingredientPhraseParsingResult;
    }

    public IgnoredIngredientWordOccurence() {
    }
}
