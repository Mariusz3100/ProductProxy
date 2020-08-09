package mariusz.ambroziak.kassistant.hibernate.statistical.model;

import mariusz.ambroziak.kassistant.hibernate.parsing.model.IngredientPhraseParsingResult;

import javax.persistence.*;

@Entity
@Table(name = "IngredientWordOccurence",schema = "statistics")
public class IngredientWordOccurence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long iwo_id;

    @ManyToOne
    private Word word;


    @OneToOne
    IngredientPhraseParsingResult ingredientPhraseParsingResult;


    public Long getIwo_id() {
        return iwo_id;
    }

    public void setIwo_id(Long iwo_id) {
        this.iwo_id = iwo_id;
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

    public IngredientWordOccurence(Word word, IngredientPhraseParsingResult ingredientPhraseParsingResult) {
        this.word = word;
        this.ingredientPhraseParsingResult = ingredientPhraseParsingResult;
    }

    public IngredientWordOccurence() {
    }
}
